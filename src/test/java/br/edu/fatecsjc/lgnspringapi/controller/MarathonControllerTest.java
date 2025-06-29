
package br.edu.fatecsjc.lgnspringapi.controller;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonRequest;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarathonControllerTest {

  private MarathonService marathonService;
  private OrganizationService organizationService;
  private MarathonController controller;

  @BeforeEach
  void setUp() {
    marathonService = mock(MarathonService.class);
    organizationService = mock(OrganizationService.class);
    controller = new MarathonController(marathonService, organizationService);
  }

  @Test
  void testGetAll() {
    MarathonDTO dto = new MarathonDTO(1L, "Test Marathon", "2025-06-05", 1L, "Test Org");
    when(marathonService.findAll()).thenReturn(List.of(dto));

    List<MarathonDTO> result = controller.getAll();

    assertEquals(1, result.size());
    assertEquals("Test Marathon", result.get(0).getName());
    verify(marathonService, times(1)).findAll();
  }

  @Test
  void testGetByIdFound() {
    MarathonDTO dto = new MarathonDTO(1L, "Test Marathon", "2025-06-05", 1L, "Test Org");
    when(marathonService.findById(1L)).thenReturn(dto);

    MarathonDTO result = controller.getById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Test Marathon", result.getName());
    verify(marathonService).findById(1L);
  }

  @Test
  void testGetByIdNotFound() {
    when(marathonService.findById(99L)).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> controller.getById(99L));
    assertEquals("Marathon not found", exception.getMessage());
  }

  @Test
  void testCreate() {
    MarathonRequest request = new MarathonRequest();
    request.setName("New Marathon");
    request.setDate("2025-06-05");
    request.setOrganizationId(1L);

    Organization organization = new Organization();
    organization.setId(1L);
    organization.setName("Test Org");

    when(organizationService.findById(1L)).thenReturn(Optional.of(organization));

    MarathonDTO dto = new MarathonDTO(1L, "New Marathon", "2025-06-05", 1L, "Test Org");
    when(marathonService.save(any(Marathon.class))).thenReturn(dto);

    MarathonDTO result = controller.create(request);

    assertNotNull(result);
    assertEquals("New Marathon", result.getName());
    assertEquals(1L, result.getOrganizationId());
    verify(organizationService).findById(1L);
    verify(marathonService).save(any(Marathon.class));
  }

  @Test
  void testCreateOrganizationNotFound() {
    MarathonRequest request = new MarathonRequest();
    request.setName("New Marathon");
    request.setDate("2025-06-05");
    request.setOrganizationId(999L);

    when(organizationService.findById(999L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> controller.create(request));
    assertEquals("Organization not found", exception.getMessage());
  }

  @Test
  void testDelete() {
    doNothing().when(marathonService).deleteById(1L);

    controller.delete(1L);

    verify(marathonService, times(1)).deleteById(1L);
  }
}
