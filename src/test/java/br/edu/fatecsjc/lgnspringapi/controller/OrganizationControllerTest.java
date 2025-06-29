package br.edu.fatecsjc.lgnspringapi.controller;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.mapper.OrganizationMapper;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class OrganizationControllerTest {

  @InjectMocks
  private OrganizationController controller;

  @Mock
  private OrganizationService service;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  private Organization organization;
  private OrganizationDTO organizationDTO;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .build();

    organization = Organization.builder()
        .id(1L)
        .name("Org Name")
        .number("123")
        .street("Main St")
        .neighborhood("Downtown")
        .cep("12345-678")
        .municipality("City")
        .state("ST")
        .build();

    organizationDTO = OrganizationMapper.toDTO(organization);
  }

  @Test
  void testGetAll() throws Exception {
    List<OrganizationDTO> listDTO = Collections.singletonList(organizationDTO);

    when(service.findAll()).thenReturn(listDTO);

    mockMvc.perform(get("/organizations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(organization.getId()))
        .andExpect(jsonPath("$[0].name").value(organization.getName()));

    verify(service, times(1)).findAll();
  }

  @Test
  void testGetByIdFound() throws Exception {
    when(service.findById(1L)).thenReturn(Optional.of(organization));

    mockMvc.perform(get("/organizations/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(organization.getId()))
        .andExpect(jsonPath("$.name").value(organization.getName()));

    verify(service, times(1)).findById(1L);
  }

  @Test
  void testGetByIdNotFound() throws Exception {
    when(service.findById(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/organizations/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));

    verify(service, times(1)).findById(1L);
  }

  @Test
  void testCreate() throws Exception {
    when(service.save(any(Organization.class))).thenReturn(organizationDTO);

    String json = objectMapper.writeValueAsString(organization);

    mockMvc.perform(post("/organizations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(organization.getId()))
        .andExpect(jsonPath("$.name").value(organization.getName()));

    verify(service, times(1)).save(any(Organization.class));
  }

  @Test
  void testUpdate() throws Exception {
    when(service.save(any(Organization.class))).thenReturn(organizationDTO);

    String json = objectMapper.writeValueAsString(organization);

    mockMvc.perform(put("/organizations/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(organization.getId()))
        .andExpect(jsonPath("$.name").value(organization.getName()));

    verify(service, times(1)).save(any(Organization.class));
  }

  @Test
  void testDelete() throws Exception {
    doNothing().when(service).deleteById(1L);

    mockMvc.perform(delete("/organizations/1"))
        .andExpect(status().isOk());

    verify(service, times(1)).deleteById(1L);
  }

  // Handler para transformar RuntimeException em 404, para teste
  static class ControllerExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public String handleNotFound(RuntimeException ex) {
      return ex.getMessage();
    }
  }
}