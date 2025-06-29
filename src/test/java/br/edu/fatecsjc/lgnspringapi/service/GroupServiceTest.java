package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceTest {

  private GroupRepository groupRepository;
  private MemberRepository memberRepository;
  private GroupConverter groupConverter;
  private GroupService groupService;

  @BeforeEach
  void setUp() {
    groupRepository = mock(GroupRepository.class);
    memberRepository = mock(MemberRepository.class);
    groupConverter = mock(GroupConverter.class);
    groupService = new GroupService(groupRepository, memberRepository, groupConverter);
  }

  @Test
  void testGetAll() {
    List<Group> groups = List.of(new Group());
    List<GroupDTO> dtos = List.of(new GroupDTO());

    when(groupRepository.findAll()).thenReturn(groups);
    when(groupConverter.convertToDto(groups)).thenReturn(dtos);

    List<GroupDTO> result = groupService.getAll();

    assertEquals(1, result.size());
    verify(groupRepository, times(1)).findAll();
  }

  @Test
  void testFindById_Success() {
    Group group = new Group();
    group.setId(1L);
    GroupDTO dto = new GroupDTO();
    dto.setId(1L);

    when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
    when(groupConverter.convertToDto(group)).thenReturn(dto);

    GroupDTO result = groupService.findById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void testFindById_NotFound() {
    when(groupRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      groupService.findById(1L);
    });

    assertEquals("Group not found with id 1", exception.getMessage());
  }

  @Test
  void testSaveWithId() {
    Group existingGroup = new Group();
    existingGroup.setId(1L);
    existingGroup.setMembers(new java.util.ArrayList<>());


    MemberDTO memberDTO = MemberDTO.builder().id(1L).name("John").age(30).build();
    GroupDTO dto = GroupDTO.builder().id(1L).name("Group A").members(List.of(memberDTO)).build();

    Group savedGroup = new Group();
    savedGroup.setId(1L);
    savedGroup.setName("Group A");

    when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));
    when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);
    when(groupConverter.convertToDto(savedGroup)).thenReturn(dto);

    GroupDTO result = groupService.save(1L, dto);

    assertNotNull(result);
    assertEquals("Group A", result.getName());
    verify(groupRepository, times(1)).save(existingGroup);
  }

  @Test
  void testSaveWithoutId() {
    GroupDTO dto = GroupDTO.builder().id(1L).name("Group A").build();

    Group groupEntity = new Group();
    groupEntity.setId(1L);
    groupEntity.setName("Group A");

    when(groupConverter.convertToEntity(dto)).thenReturn(groupEntity);
    when(groupRepository.save(groupEntity)).thenReturn(groupEntity);
    when(groupConverter.convertToDto(groupEntity)).thenReturn(dto);

    GroupDTO result = groupService.save(dto);

    assertNotNull(result);
    assertEquals("Group A", result.getName());
    verify(groupRepository, times(1)).save(groupEntity);
  }

  @Test
  void testDelete() {
    doNothing().when(groupRepository).deleteById(1L);

    groupService.delete(1L);

    verify(groupRepository, times(1)).deleteById(1L);
  }
}