
package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {

  private OrganizationRepository repository;
  private OrganizationService service;

  @BeforeEach
  void setUp() {
    repository = mock(OrganizationRepository.class);
    service = new OrganizationService(repository);
  }

  @Test
  void testFindAll() {
    Organization organization = Organization.builder()
        .id(1L)
        .name("Org Test")
        .build();

    when(repository.findAll()).thenReturn(List.of(organization));

    List<OrganizationDTO> result = service.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Org Test", result.get(0).getName());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testFindById_Success() {
    Organization organization = Organization.builder()
        .id(1L)
        .name("Org Test")
        .build();

    when(repository.findById(1L)).thenReturn(Optional.of(organization));

    Optional<Organization> result = service.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
    assertEquals("Org Test", result.get().getName());
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testFindById_NotFound() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    Optional<Organization> result = service.findById(1L);

    assertFalse(result.isPresent());
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testSave() {
    Organization organization = Organization.builder()
        .id(1L)
        .name("Org Save Test")
        .build();

    when(repository.save(organization)).thenReturn(organization);

    OrganizationDTO result = service.save(organization);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Org Save Test", result.getName());
    verify(repository, times(1)).save(organization);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(1L);

    service.deleteById(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}
