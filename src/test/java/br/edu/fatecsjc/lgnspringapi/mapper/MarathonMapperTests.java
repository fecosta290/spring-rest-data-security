package br.edu.fatecsjc.lgnspringapi.mapper;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MarathonMapperTest {

  @Test
  @DisplayName("Deve mapear Marathon para MarathonDTO corretamente")
  void testToDTO() {
    Organization organization = Organization.builder()
        .id(1L)
        .name("Fatec")
        .build();

    Marathon marathon = Marathon.builder()
        .id(10L)
        .name("Maratona de Teste")
        .date(LocalDate.of(2025, 6, 1).toString())
        .organization(organization)
        .build();

    MarathonDTO dto = MarathonMapper.toDTO(marathon);

    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(10L);
    assertThat(dto.getName()).isEqualTo("Maratona de Teste");
    assertThat(dto.getDate()).isEqualTo("2025-06-01");
    assertThat(dto.getOrganizationId()).isEqualTo(1L);
    assertThat(dto.getOrganizationName()).isEqualTo("Fatec");
  }

  @Test
  @DisplayName("Deve mapear MarathonDTO para Marathon corretamente")
  void testToEntity() {
    MarathonDTO dto = MarathonDTO.builder()
        .id(10L)
        .name("Maratona de Teste")
        .date(LocalDate.of(2025, 6, 1).toString())
        .organizationId(1L)
        .organizationName("Fatec")
        .build();

    Organization organization = Organization.builder()
        .id(1L)
        .name("Fatec")
        .build();

    Marathon entity = MarathonMapper.toEntity(dto, organization);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isEqualTo(10L);
    assertThat(entity.getName()).isEqualTo("Maratona de Teste");
    assertThat(entity.getDate()).isEqualTo("2025-06-01");
    assertThat(entity.getOrganization()).isEqualTo(organization);
  }

  @Test
  @DisplayName("Deve retornar null quando Marathon ou DTO for null")
  void testNullCases() {
    assertThat(MarathonMapper.toDTO(null)).isNull();
    assertThat(MarathonMapper.toEntity(null, null)).isNull();
  }
}