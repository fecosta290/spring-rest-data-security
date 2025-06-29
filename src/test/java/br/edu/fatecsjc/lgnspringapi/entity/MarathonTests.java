
package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MarathonTest {

  private Marathon marathon;
  private Organization organization;
  private MemberMarathon memberMarathon1;
  private MemberMarathon memberMarathon2;

  @BeforeEach
  void setUp() {
    organization = Organization.builder()
        .id(1L)
        .name("Red Cross")
        .build();

    memberMarathon1 = MemberMarathon.builder()
        .id(1L)
        .name("João Silva")
        .time(7200L) // 2 horas em segundos
        .build();

    memberMarathon2 = MemberMarathon.builder()
        .id(2L)
        .name("Maria Oliveira")
        .time(7800L) // 2h10m em segundos
        .build();

    marathon = Marathon.builder()
        .id(1L)
        .name("São Paulo Marathon")
        .date("2025-06-15")
        .organization(organization)
        .memberMarathons(new java.util.ArrayList<>(List.of(memberMarathon1, memberMarathon2)))
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    assertThat(marathon).isNotNull();
    assertThat(marathon.getId()).isEqualTo(1L);
    assertThat(marathon.getName()).isEqualTo("São Paulo Marathon");
    assertThat(marathon.getDate()).isEqualTo("2025-06-15");
    assertThat(marathon.getOrganization().getName()).isEqualTo("Red Cross");
    assertThat(marathon.getMemberMarathons()).hasSize(2);
    assertThat(marathon.getMemberMarathons().get(0).getName()).isEqualTo("João Silva");
    assertThat(marathon.getMemberMarathons().get(1).getTime()).isEqualTo(7800L);
  }

  @Test
  void shouldSetAndGetId() {
    marathon.setId(99L);
    assertThat(marathon.getId()).isEqualTo(99L);
  }

  @Test
  void shouldSetAndGetName() {
    marathon.setName("Rio Marathon");
    assertThat(marathon.getName()).isEqualTo("Rio Marathon");
  }

  @Test
  void shouldSetAndGetDate() {
    marathon.setDate("2024-12-31");
    assertThat(marathon.getDate()).isEqualTo("2024-12-31");
  }

  @Test
  void shouldSetAndGetOrganization() {
    Organization newOrg = Organization.builder()
        .id(2L)
        .name("Green Peace")
        .build();

    marathon.setOrganization(newOrg);

    assertThat(marathon.getOrganization()).isEqualTo(newOrg);
    assertThat(marathon.getOrganization().getName()).isEqualTo("Green Peace");
  }

  @Test
  void shouldAddMemberMarathonToList() {
    MemberMarathon newMember = MemberMarathon.builder()
        .id(3L)
        .name("Carlos Souza")
        .time(8000L)
        .build();

    marathon.getMemberMarathons().add(newMember);

    assertThat(marathon.getMemberMarathons()).hasSize(3);
    assertThat(marathon.getMemberMarathons().get(2).getName()).isEqualTo("Carlos Souza");
  }

  @Test
  void shouldRemoveMemberMarathonFromList() {
    marathon.getMemberMarathons().remove(memberMarathon1);

    assertThat(marathon.getMemberMarathons()).hasSize(1);
    assertThat(marathon.getMemberMarathons()).doesNotContain(memberMarathon1);
  }
}
