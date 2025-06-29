package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

  private Member member;
  private Group group;

  @BeforeEach
  void setUp() {
    group = Group.builder()
        .id(1L)
        .name("Grupo Legal")
        .build();

    member = Member.builder()
        .id(1L)
        .name("João Silva")
        .age(25)
        .group(group)
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    assertThat(member).isNotNull();
    assertThat(member.getId()).isEqualTo(1L);
    assertThat(member.getName()).isEqualTo("João Silva");
    assertThat(member.getAge()).isEqualTo(25);
    assertThat(member.getGroup().getName()).isEqualTo("Grupo Legal");
  }

  @Test
  void shouldSetAndGetId() {
    member.setId(99L);

    assertThat(member.getId()).isEqualTo(99L);
  }

  @Test
  void shouldSetAndGetName() {
    member.setName("Maria Oliveira");

    assertThat(member.getName()).isEqualTo("Maria Oliveira");
  }

  @Test
  void shouldSetAndGetAge() {
    member.setAge(30);

    assertThat(member.getAge()).isEqualTo(30);
  }

  @Test
  void shouldSetAndGetGroup() {
    // Given
    Group newGroup = Group.builder()
        .id(2L)
        .name("Novo Grupo")
        .build();

    member.setGroup(newGroup);

    assertThat(member.getGroup()).isEqualTo(newGroup);
    assertThat(member.getGroup().getName()).isEqualTo("Novo Grupo");
  }

  @Test
  void shouldToStringExcludeGroupDueToAnnotation() {
    String toString = member.toString();

    assertThat(toString).contains("name=João Silva");
    assertThat(toString).doesNotContain("group=");
  }
}