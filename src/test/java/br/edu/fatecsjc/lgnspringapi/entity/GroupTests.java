
package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroupTest {

  private Group group;
  private Member member1;
  private Member member2;

  @BeforeEach
  void setUp() {
    member1 = Member.builder()
        .id(1L)
        .name("João")
        .age(25)
        .build();

    member2 = Member.builder()
        .id(2L)
        .name("Maria")
        .age(30)
        .build();

    group = Group.builder()
        .id(1L)
        .name("Grupo Legal")
        .members(new java.util.ArrayList<>(List.of(member1, member2)))
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    // Then
    assertThat(group).isNotNull();
    assertThat(group.getId()).isEqualTo(1L);
    assertThat(group.getName()).isEqualTo("Grupo Legal");
    assertThat(group.getMembers()).hasSize(2);
    assertThat(group.getMembers().get(0).getName()).isEqualTo("João");
    assertThat(group.getMembers().get(1).getName()).isEqualTo("Maria");
  }

  @Test
  void shouldSetAndGetId() {
    // When
    group.setId(99L);

    // Then
    assertThat(group.getId()).isEqualTo(99L);
  }

  @Test
  void shouldSetAndGetName() {
    // When
    group.setName("Novo Nome");

    // Then
    assertThat(group.getName()).isEqualTo("Novo Nome");
  }

  @Test
  void shouldAddMemberToList() {
    // Given
    Member newMember = Member.builder()
        .id(3L)
        .name("Carlos")
        .age(28)
        .build();

    // When
    group.getMembers().add(newMember);

    // Then
    assertThat(group.getMembers()).hasSize(3);
    assertThat(group.getMembers().get(2).getName()).isEqualTo("Carlos");
  }

  @Test
  void shouldRemoveMemberFromList() {
    // When
    group.getMembers().remove(member1);

    // Then
    assertThat(group.getMembers()).hasSize(1);
    assertThat(group.getMembers().contains(member1)).isFalse();
  }

  @Test
  void shouldToStringExcludeMembersDueToAnnotation() {
    // When
    String toString = group.toString();

    // Then
    assertThat(toString).contains("name=Grupo Legal");
    assertThat(toString).doesNotContain("members");
  }

  @Test
  void shouldDefaultMembersListBeInitialized() {
    // Given
    Group emptyGroup = new Group();

    // Then
    assertThat(emptyGroup.getMembers()).isNotNull();
    assertThat(emptyGroup.getMembers()).isEmpty();
  }
}
