package br.edu.fatecsjc.lgnspringapi.mapper;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationMapperTest {

  @Test
  @DisplayName("Deve mapear Organization para OrganizationDTO corretamente")
  void testToDTO() {
    Marathon marathon = Marathon.builder()
        .id(100L)
        .name("Maratona de Teste")
        .date("2025-06-01")
        .build();

    Organization organization = Organization.builder()
        .id(1L)
        .name("Fatec")
        .number("123")
        .street("Rua A")
        .neighborhood("Centro")
        .cep("12345-678")
        .municipality("São José dos Campos")
        .state("SP")
        .institutionName("Fatec SJC")
        .hostCountry("Brasil")
        .marathons(List.of(marathon))
        .build();

    OrganizationDTO dto = OrganizationMapper.toDTO(organization);

    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getName()).isEqualTo("Fatec");
    assertThat(dto.getNumber()).isEqualTo("123");
    assertThat(dto.getStreet()).isEqualTo("Rua A");
    assertThat(dto.getNeighborhood()).isEqualTo("Centro");
    assertThat(dto.getCep()).isEqualTo("12345-678");
    assertThat(dto.getMunicipality()).isEqualTo("São José dos Campos");
    assertThat(dto.getState()).isEqualTo("SP");
    assertThat(dto.getMarathons()).hasSize(1);

    MarathonDTO marathonDTO = dto.getMarathons().get(0);
    assertThat(marathonDTO.getId()).isEqualTo(100L);
    assertThat(marathonDTO.getName()).isEqualTo("Maratona de Teste");
    assertThat(marathonDTO.getDate()).isEqualTo("2025-06-01");
  }

  @Test
  @DisplayName("Deve mapear OrganizationDTO para Organization corretamente")
  void testToEntity() {
    MarathonDTO marathonDTO = MarathonDTO.builder()
        .id(100L)
        .name("Maratona de Teste")
        .date("2025-06-01")
        .organizationId(1L)
        .organizationName("Fatec")
        .build();

    OrganizationDTO dto = new OrganizationDTO(
        1L,
        "Fatec",
        "123",
        "Rua A",
        "Centro",
        "12345-678",
        "São José dos Campos",
        "SP",
        List.of(marathonDTO));

    Organization entity = OrganizationMapper.toEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isEqualTo(1L);
    assertThat(entity.getName()).isEqualTo("Fatec");
    assertThat(entity.getNumber()).isEqualTo("123");
    assertThat(entity.getStreet()).isEqualTo("Rua A");
    assertThat(entity.getNeighborhood()).isEqualTo("Centro");
    assertThat(entity.getCep()).isEqualTo("12345-678");
    assertThat(entity.getMunicipality()).isEqualTo("São José dos Campos");
    assertThat(entity.getState()).isEqualTo("SP");
    assertThat(entity.getMarathons()).isNull(); 
  }

  @Test
  @DisplayName("Deve retornar null quando Organization ou DTO for null")
  void testNullCases() {
    assertThat(OrganizationMapper.toDTO(null)).isNull();
    assertThat(OrganizationMapper.toEntity(null)).isNull();
  }

  @Test
  @DisplayName("Deve retornar lista vazia de marathons no DTO quando for null na entidade")
  void testEmptyMarathons() {
    Organization organization = Organization.builder()
        .id(2L)
        .name("Organization Sem Maratona")
        .marathons(null)
        .build();

    OrganizationDTO dto = OrganizationMapper.toDTO(organization);

    assertThat(dto).isNotNull();
    assertThat(dto.getMarathons()).isEmpty();
  }
}