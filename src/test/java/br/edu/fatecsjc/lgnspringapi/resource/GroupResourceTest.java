package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GroupResourceTest {

  private MockMvc mockMvc;

  @Mock
  private GroupService groupService;

  @InjectMocks
  private GroupResource groupResource;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private GroupDTO groupDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(groupResource).build();

    groupDTO = GroupDTO.builder()
        .id(1L)
        .name("Group Test")
        .members(Collections.emptyList())
        .build();
  }

  @Test
  void shouldGetAllGroups() throws Exception {
    when(groupService.getAll()).thenReturn(List.of(groupDTO));

    mockMvc.perform(get("/group"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(groupDTO.getId()))
        .andExpect(jsonPath("$[0].name").value(groupDTO.getName()));

    verify(groupService, times(1)).getAll();
  }

  @Test
  void shouldGetGroupById() throws Exception {
    when(groupService.findById(1L)).thenReturn(groupDTO);

    mockMvc.perform(get("/group/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(groupDTO.getId()))
        .andExpect(jsonPath("$.name").value(groupDTO.getName()));

    verify(groupService, times(1)).findById(1L);
  }

  @Test
  void shouldCreateGroup() throws Exception {
    when(groupService.save(any(GroupDTO.class))).thenReturn(groupDTO);

    mockMvc.perform(post("/group")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(groupDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(groupDTO.getId()))
        .andExpect(jsonPath("$.name").value(groupDTO.getName()));

    verify(groupService, times(1)).save(any(GroupDTO.class));
  }

  @Test
  void shouldUpdateGroup() throws Exception {
    when(groupService.save(eq(1L), any(GroupDTO.class))).thenReturn(groupDTO);

    mockMvc.perform(put("/group/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(groupDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(groupDTO.getId()))
        .andExpect(jsonPath("$.name").value(groupDTO.getName()));

    verify(groupService, times(1)).save(eq(1L), any(GroupDTO.class));
  }

  @Test
  void shouldDeleteGroup() throws Exception {
    doNothing().when(groupService).delete(1L);

    mockMvc.perform(delete("/group/1"))
        .andExpect(status().isNoContent());

    verify(groupService, times(1)).delete(1L);
  }
}