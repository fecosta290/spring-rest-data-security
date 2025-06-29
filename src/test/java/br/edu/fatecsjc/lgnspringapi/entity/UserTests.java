package br.edu.fatecsjc.lgnspringapi.entity;

import br.edu.fatecsjc.lgnspringapi.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .firstName("John")
        .lastName("Doe")
        .email("john@example.com")
        .password("secret")
        .role(Role.ADMIN)
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(1L);
    assertThat(user.getFirstName()).isEqualTo("John");
    assertThat(user.getLastName()).isEqualTo("Doe");
    assertThat(user.getEmail()).isEqualTo("john@example.com");
    assertThat(user.getPassword()).isEqualTo("secret");
    assertThat(user.getRole()).isEqualTo(Role.ADMIN);
  }

  @Test
  void shouldSetAndGetFirstName() {
    user.setFirstName("Jane");
    assertThat(user.getFirstName()).isEqualTo("Jane");
  }

  @Test
  void shouldSetAndGetLastName() {
    user.setLastName("Smith");
    assertThat(user.getLastName()).isEqualTo("Smith");
  }

  @Test
  void shouldSetAndGetEmail() {
    user.setEmail("jane@example.com");
    assertThat(user.getEmail()).isEqualTo("jane@example.com");
  }

  @Test
  void shouldSetAndGetPassword() {
    user.setPassword("newpass");
    assertThat(user.getPassword()).isEqualTo("newpass");
  }

  @Test
  void shouldSetAndGetRole() {
    user.setRole(Role.USER);
    assertThat(user.getRole()).isEqualTo(Role.USER);
  }

  @Test
  void getUsername_shouldReturnEmail() {
    assertThat(user.getUsername()).isEqualTo("john@example.com");
  }

  @Test
  void getPassword_shouldReturnStoredPassword() {
    assertThat(user.getPassword()).isEqualTo("secret");
  }

  @Test
  void getAuthorities_shouldReturnRoleAuthorities() {
    assertThat(user.getAuthorities()).isEqualTo(Role.ADMIN.getAuthorities());
  }

  @Test
  void accountNonExpired_shouldReturnTrue() {
    assertThat(user.isAccountNonExpired()).isTrue();
  }

  @Test
  void accountNonLocked_shouldReturnTrue() {
    assertThat(user.isAccountNonLocked()).isTrue();
  }

  @Test
  void credentialsNonExpired_shouldReturnTrue() {
    assertThat(user.isCredentialsNonExpired()).isTrue();
  }

  @Test
  void isEnabled_shouldReturnTrue() {
    assertThat(user.isEnabled()).isTrue();
  }
}