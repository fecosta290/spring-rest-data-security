
package br.edu.fatecsjc.lgnspringapi.controller;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberControllerTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private GroupRepository groupRepository;

  @InjectMocks
  @Spy
  private MemberController memberController;

  private Group group;
  private Member member;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    group = Group.builder()
        .id(1L)
        .name("Group A")
        .build();

    member = Member.builder()
        .id(1L)
        .name("John Doe")
        .age(30)
        .group(group)
        .build();
  }

  // GET /members
  @Test
  void testGetAllMembers() {
    when(memberRepository.findAll()).thenReturn(List.of(member));

    List<Member> result = memberController.getAllMembers();

    assertEquals(1, result.size());
    assertEquals("John Doe", result.get(0).getName());
    verify(memberRepository, times(1)).findAll();
  }

  // GET /members/{id} - success
  @Test
  void testGetMemberByIdFound() {
    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

    ResponseEntity<Member> response = memberController.getMemberById(1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(member, response.getBody());
  }

  // GET /members/{id} - not found
  @Test
  void testGetMemberByIdNotFound() {
    when(memberRepository.findById(2L)).thenReturn(Optional.empty());

    ResponseEntity<Member> response = memberController.getMemberById(2L);

    assertEquals(404, response.getStatusCodeValue());
  }

  // POST /members - success
  @Test
  void testCreateMemberSuccess() {
    when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
    when(memberRepository.save(any(Member.class))).thenReturn(member);

    ResponseEntity<Member> response = memberController.createMember(member);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(member, response.getBody());
  }

  // POST /members - group not found
  @Test
  void testCreateMemberGroupNotFound() {
    when(groupRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseEntity<Member> response = memberController.createMember(member);

    assertEquals(400, response.getStatusCodeValue());
  }

  // PUT /members/{id} - success
  @Test
  void testUpdateMemberSuccess() {
    Member updatedDetails = Member.builder()
        .name("Jane Doe")
        .age(25)
        .group(group)
        .build();

    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
    when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
    when(memberRepository.save(any(Member.class))).thenReturn(updatedDetails);

    ResponseEntity<Member> response = memberController.updateMember(1L, updatedDetails);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Jane Doe", response.getBody().getName());
    assertEquals(25, response.getBody().getAge());
  }

  // PUT /members/{id} - member not found
  @Test
  void testUpdateMemberNotFound() {
    when(memberRepository.findById(2L)).thenReturn(Optional.empty());

    ResponseEntity<Member> response = memberController.updateMember(2L, member);

    assertEquals(404, response.getStatusCodeValue());
  }

  // PUT /members/{id} - group not found
  @Test
  void testUpdateMemberGroupNotFound() {
    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
    when(groupRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseEntity<Member> response = memberController.updateMember(1L, member);

    assertEquals(400, response.getStatusCodeValue());
  }

  // DELETE /members/{id} - success
  @Test
  void testDeleteMemberSuccess() {
    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

    ResponseEntity<Void> response = memberController.deleteMember(1L);

    assertEquals(204, response.getStatusCodeValue());
    verify(memberRepository, times(1)).deleteById(1L);
  }

  // DELETE /members/{id} - not found
  @Test
  void testDeleteMemberNotFound() {
    when(memberRepository.findById(2L)).thenReturn(Optional.empty());

    ResponseEntity<Void> response = memberController.deleteMember(2L);

    assertEquals(404, response.getStatusCodeValue());
  }
}
