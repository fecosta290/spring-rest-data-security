
package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarathonServiceTest {

  private MarathonRepository repository;
  private MarathonService service;

  @BeforeEach
  void setUp() {
    repository = mock(MarathonRepository.class);
    service = new MarathonService(repository);
  }

  @Test
  void testFindAll() {
    Marathon marathon = Marathon.builder().id(1L).name("Test Marathon").build();
    when(repository.findAll()).thenReturn(List.of(marathon));

    List<MarathonDTO> result = service.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Test Marathon", result.get(0).getName());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testFindById_Found() {
    Marathon marathon = Marathon.builder().id(1L).name("Test Marathon").build();
    when(repository.findById(1L)).thenReturn(Optional.of(marathon));

    MarathonDTO result = service.findById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Test Marathon", result.getName());
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testFindById_NotFound() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    MarathonDTO result = service.findById(1L);

    assertNull(result);
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testSave() {
    Marathon marathon = Marathon.builder().id(1L).name("Test Marathon").build();
    when(repository.save(marathon)).thenReturn(marathon);

    MarathonDTO result = service.save(marathon);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Test Marathon", result.getName());
    verify(repository, times(1)).save(marathon);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(1L);

    service.deleteById(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}
