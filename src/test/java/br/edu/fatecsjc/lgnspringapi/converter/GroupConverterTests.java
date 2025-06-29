
package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupConverterTest {

  private GroupConverter converter;

  @BeforeEach
  void setup() {
    converter = new GroupConverter(new ModelMapper());
  }

  @Test
  void shouldConvertDtoToEntity() {
    GroupDTO dto = new GroupDTO();
    dto.setName("Test Group");

    Group group = converter.convertToEntity(dto);

    assertNotNull(group);
    assertEquals("Test Group", group.getName());
    assertNull(group.getId()); 
  }

  @Test
  void shouldConvertEntityToDto() {
    Group group = new Group();
    group.setId(1L);
    group.setName("Test Group");

    GroupDTO dto = converter.convertToDto(group);

    assertNotNull(dto);
    assertEquals("Test Group", dto.getName());
  }

  @Test
  void shouldConvertListDtoToEntity() {
    GroupDTO dto = new GroupDTO();
    dto.setName("Test Group");

    List<Group> groups = converter.convertToEntity(Collections.singletonList(dto));

    assertNotNull(groups);
    assertEquals(1, groups.size());
    assertEquals("Test Group", groups.get(0).getName());
  }

  @Test
  void shouldConvertListEntityToDto() {
    Group group = new Group();
    group.setId(1L);
    group.setName("Test Group");

    List<GroupDTO> dtos = converter.convertToDto(Collections.singletonList(group));

    assertNotNull(dtos);
    assertEquals(1, dtos.size());
    assertEquals("Test Group", dtos.get(0).getName());
  }

  @Test
  void shouldKeepMembersReferenceWhenConvertingDtoToEntity() {
    GroupDTO dto = new GroupDTO();
    dto.setName("Test Group");

    // Simular que o DTO tem membros
    var memberDto = new br.edu.fatecsjc.lgnspringapi.dto.MemberDTO();
    memberDto.setName("Member 1");
    dto.setMembers(List.of(memberDto));

    Group group = converter.convertToEntity(dto);

    assertNotNull(group.getMembers());
    assertEquals(1, group.getMembers().size());
    assertEquals(group, group.getMembers().get(0).getGroup());
  }
}
