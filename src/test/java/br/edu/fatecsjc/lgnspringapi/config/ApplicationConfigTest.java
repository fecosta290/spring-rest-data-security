
package br.edu.fatecsjc.lgnspringapi.config;

import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationConfigTest {

  private UserRepository userRepository;
  private ApplicationConfig applicationConfig;

  @BeforeEach
  void setUp() {
    userRepository = Mockito.mock(UserRepository.class);
    applicationConfig = new ApplicationConfig(userRepository);
  }

  @Test
  @DisplayName("Deve carregar UserDetails corretamente quando usuário existe")
  void shouldLoadUserByUsername() {
    User user = User.builder()
        .id(1L)
        .email("test@example.com")
        .password("password")
        .role(Role.ADMIN)
        .build();

    when(userRepository.findByEmail("test@example.com"))
        .thenReturn(Optional.of(user));

    UserDetails userDetails = applicationConfig.userDetailsService()
        .loadUserByUsername("test@example.com");

    assertNotNull(userDetails);
    assertEquals("test@example.com", userDetails.getUsername());
    verify(userRepository, times(1)).findByEmail("test@example.com");
  }

  @Test
  @DisplayName("Deve lançar UsernameNotFoundException quando usuário não existe")
  void shouldThrowWhenUserNotFound() {
    when(userRepository.findByEmail("notfound@example.com"))
        .thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> applicationConfig.userDetailsService()
        .loadUserByUsername("notfound@example.com"));

    verify(userRepository, times(1)).findByEmail("notfound@example.com");
  }

  @Test
  @DisplayName("Deve retornar um PasswordEncoder não nulo")
  void shouldReturnPasswordEncoder() {
    PasswordEncoder encoder = applicationConfig.passwordEncoder();
    assertNotNull(encoder);

    String rawPassword = "123456";
    String encodedPassword = encoder.encode(rawPassword);

    assertTrue(encoder.matches(rawPassword, encodedPassword));
  }

  @Test
  @DisplayName("Deve retornar um AuthenticationProvider não nulo")
  void shouldReturnAuthenticationProvider() {
    AuthenticationProvider authProvider = applicationConfig.authenticationProvider();
    assertNotNull(authProvider);
  }
}
