package br.edu.fatecsjc.lgnspringapi.entity;

import br.edu.fatecsjc.lgnspringapi.enums.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

  private Token token;
  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .email("john_doe")
        .password("secret")
        .build();

    token = Token.builder()
        .id(1L)
        .token("abc123xyz789")
        .tokenType(TokenType.BEARER)
        .revoked(false)
        .expired(false)
        .user(user)
        .build();
  }

  @Test
  void shouldInitializeEntityWithBuilder() {
    // Then
    assertThat(token).isNotNull();
    assertThat(token.getId()).isEqualTo(1L);
    assertThat(token.getToken()).isEqualTo("abc123xyz789");
    assertThat(token.getTokenType()).isEqualTo(TokenType.BEARER);
    assertThat(token.isRevoked()).isFalse();
    assertThat(token.isExpired()).isFalse();
    assertThat(token.getUser().getUsername()).isEqualTo("john_doe");
  }

  @Test
  void shouldSetAndGetId() {
    // When
    token.setId(99L);

    // Then
    assertThat(token.getId()).isEqualTo(99L);
  }

  @Test
  void shouldSetAndGetToken() {
    // When
    token.setToken("new_token_456");

    // Then
    assertThat(token.getToken()).isEqualTo("new_token_456");
  }

  @Test
  void shouldSetAndGetType() {
    // When
    token.setTokenType(TokenType.BEARER);

    // Then
    assertThat(token.getTokenType()).isEqualTo(TokenType.BEARER);
  }

  @Test
  void shouldSetAndGetRevokedStatus() {
    // When
    token.setRevoked(true);

    // Then
    assertThat(token.isRevoked()).isTrue();
  }

  @Test
  void shouldSetAndGetExpiredStatus() {
    // When
    token.setExpired(true);

    // Then
    assertThat(token.isExpired()).isTrue();
  }

  @Test
  void shouldSetAndGetUser() {
    // Given
    User newUser = User.builder()
        .id(2L)
        .email("jane_doe")
        .password("pass123")
        .build();

    // When
    token.setUser(newUser);

    // Then
    assertThat(token.getUser()).isEqualTo(newUser);
    assertThat(token.getUser().getUsername()).isEqualTo("jane_doe");
  }
}