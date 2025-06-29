package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationTest {

  private Organization organization;
  private Marathon marathon1;
  private Marathon marathon2;

  @BeforeEach
  void setUp() {
    marathon1 = Marathon.builder()
        .id(1L)
        .name("São Paulo Marathon")
        .date("2025-06-15")
        .build();

    marathon2 = Marathon.builder()
        .id(2L)
        .name("Rio Marathon")
        .date("2025-07-20")
        .build();

    organization = Organization.builder()
        .id(1L)
        .name("Red Cross")
        .number("123")
        .street("Rua A")
        .neighborhood("Centro")
        .cep("01000-000")
        .municipality("São Paulo")
        .state("SP")
        .institutionName("Red Cross Institution")
        .hostCountry("Brazil")
        .marathons(new java.util.ArrayList<>(List.of(marathon1, marathon2)))
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    // Then
    assertThat(organization).isNotNull();
    assertThat(organization.getId()).isEqualTo(1L);
    assertThat(organization.getName()).isEqualTo("Red Cross");
    assertThat(organization.getNumber()).isEqualTo("123");
    assertThat(organization.getStreet()).isEqualTo("Rua A");
    assertThat(organization.getNeighborhood()).isEqualTo("Centro");
    assertThat(organization.getCep()).isEqualTo("01000-000");
    assertThat(organization.getMunicipality()).isEqualTo("São Paulo");
    assertThat(organization.getState()).isEqualTo("SP");
    assertThat(organization.getInstitutionName()).isEqualTo("Red Cross Institution");
    assertThat(organization.getHostCountry()).isEqualTo("Brazil");
    assertThat(organization.getMarathons()).hasSize(2);
    assertThat(organization.getMarathons().get(0).getName()).isEqualTo("São Paulo Marathon");
    assertThat(organization.getMarathons().get(1).getName()).isEqualTo("Rio Marathon");
  }

  @Test
  void shouldSetAndGetId() {
    // When
    organization.setId(99L);

    // Then
    assertThat(organization.getId()).isEqualTo(99L);
  }

  @Test
  void shouldSetAndGetName() {
    // When
    organization.setName("Green Peace");

    // Then
    assertThat(organization.getName()).isEqualTo("Green Peace");
  }

  @Test
  void shouldSetAndGetAddressFields() {
    // When
    organization.setNumber("456");
    organization.setStreet("Rua B");
    organization.setNeighborhood("Jardim");
    organization.setCep("02000-000");
    organization.setMunicipality("Rio de Janeiro");
    organization.setState("RJ");

    // Then
    assertThat(organization.getNumber()).isEqualTo("456");
    assertThat(organization.getStreet()).isEqualTo("Rua B");
    assertThat(organization.getNeighborhood()).isEqualTo("Jardim");
    assertThat(organization.getCep()).isEqualTo("02000-000");
    assertThat(organization.getMunicipality()).isEqualTo("Rio de Janeiro");
    assertThat(organization.getState()).isEqualTo("RJ");
  }

  @Test
  void shouldSetAndGetInstitutionAndHostCountry() {
    // When
    organization.setInstitutionName("Nova Instituição");
    organization.setHostCountry("USA");

    // Then
    assertThat(organization.getInstitutionName()).isEqualTo("Nova Instituição");
    assertThat(organization.getHostCountry()).isEqualTo("USA");
  }

  @Test
  void shouldAddMarathonToList() {
    // Given
    Marathon newMarathon = Marathon.builder()
        .id(3L)
        .name("Brasília Marathon")
        .date("2025-08-10")
        .build();

    // When
    organization.getMarathons().add(newMarathon);

    // Then
    assertThat(organization.getMarathons()).hasSize(3);
    assertThat(organization.getMarathons().get(2).getName()).isEqualTo("Brasília Marathon");
  }

  @Test
  void shouldRemoveMarathonFromList() {
    // When
    organization.getMarathons().remove(marathon1);

    // Then
    assertThat(organization.getMarathons()).hasSize(1);
    assertThat(organization.getMarathons()).doesNotContain(marathon1);
  }
}