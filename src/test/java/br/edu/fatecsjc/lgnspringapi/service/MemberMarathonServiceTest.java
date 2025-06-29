
package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.entity.MemberMarathon;
import br.edu.fatecsjc.lgnspringapi.repository.MemberMarathonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberMarathonServiceTest {

  private MemberMarathonRepository repository;
  private MemberMarathonService service;

  @BeforeEach
  void setUp() {
    repository = mock(MemberMarathonRepository.class);
    service = new MemberMarathonService(repository);
  }

  @Test
  void testFindAll() {
    MemberMarathon memberMarathon = MemberMarathon.builder()
        .id(1L)
        .member(Member.builder().id(1L).name("John Doe").build())
        .marathon(Marathon.builder().id(1L).name("Test Marathon").date("2025-10-10").build())
        .build();

    when(repository.findAll()).thenReturn(List.of(memberMarathon));

    List<MemberMarathon> result = service.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals("John Doe", result.get(0).getMember().getName());
    assertEquals("Test Marathon", result.get(0).getMarathon().getName());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testFindById_Success() {
    MemberMarathon memberMarathon = MemberMarathon.builder()
        .id(1L)
        .member(Member.builder().id(1L).name("John Doe").build())
        .marathon(Marathon.builder().id(1L).name("Test Marathon").date("2025-10-10").build())
        .build();

    when(repository.findById(1L)).thenReturn(Optional.of(memberMarathon));

    Optional<MemberMarathon> result = service.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testFindById_NotFound() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    Optional<MemberMarathon> result = service.findById(1L);

    assertFalse(result.isPresent());
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testSave() {
    MemberMarathon memberMarathon = MemberMarathon.builder()
        .id(1L)
        .member(Member.builder().id(1L).name("John Doe").build())
        .marathon(Marathon.builder().id(1L).name("Test Marathon").date("2025-10-10").build())
        .build();

    when(repository.save(memberMarathon)).thenReturn(memberMarathon);

    MemberMarathon result = service.save(memberMarathon);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(repository, times(1)).save(memberMarathon);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(1L);

    service.deleteById(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}
