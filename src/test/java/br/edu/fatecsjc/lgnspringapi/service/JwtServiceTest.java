
package br.edu.fatecsjc.lgnspringapi.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.lang.reflect.Field;

import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

  private JwtService jwtService;

  // Valores fixos para teste
  private final String secretKey = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
  private final long jwtExpiration = 3600000; // 1h
  private final long refreshExpiration = 86400000; // 24h

  private UserDetails userDetails;

  @BeforeEach
  void setUp() throws Exception {
    jwtService = new JwtService();

    setPrivateField("secretKey", secretKey);
    setPrivateField("jwtExpiration", jwtExpiration);
    setPrivateField("refreshExpiration", refreshExpiration);

    userDetails = User.withUsername("john")
        .password("password")
        .authorities(Collections.emptyList())
        .build();
  }

  private void setPrivateField(String fieldName, Object value) throws Exception {
    Field field = JwtService.class.getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(jwtService, value);
  }

  @Test
  void extractUsername_returnsCorrectUsername() {
    String token = jwtService.generateToken(userDetails);

    String username = jwtService.extractUsername(token);

    assertThat(username).isEqualTo("john");
  }

  @Test
  void generateToken_returnsValidToken() {
    String token = jwtService.generateToken(userDetails);

    assertThat(token).isNotBlank();
  }

  @Test
  void isTokenValid_withValidToken_returnsTrue() {
    String token = jwtService.generateToken(userDetails);

    boolean isValid = jwtService.isTokenValid(token, userDetails);

    assertThat(isValid).isTrue();
  }

  @Test
  void isTokenValid_withInvalidUsername_returnsFalse() {
    String token = jwtService.generateToken(userDetails);

    UserDetails otherUser = User.withUsername("jane").password("password").build();

    boolean isValid = jwtService.isTokenValid(token, otherUser);

    assertThat(isValid).isFalse();
  }

  @Test
  void isTokenValid_withExpiredToken_returnsFalse() throws Exception {
    setPrivateField("jwtExpiration", 500L); 

    // Gera token com essa expiração curta
    String token = jwtService.generateToken(userDetails);

    Thread.sleep(600); 

    boolean isValid = jwtService.isTokenValid(token, userDetails);

    assertThat(isValid).isFalse();
  }

  @Test
  void generateRefreshToken_returnsValidRefreshToken() {
    String refreshToken = jwtService.generateRefreshToken(userDetails);

    assertThat(refreshToken).isNotBlank();
  }

  @Test
  void extractClaim_throwsOnInvalidToken() {
    assertThatThrownBy(() -> jwtService.extractClaim("invalidtoken", Claims::getSubject))
        .isInstanceOf(io.jsonwebtoken.JwtException.class);
  }
}
