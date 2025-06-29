
package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.ChangePasswordRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  @Test
  void changePassword_whenCurrentPasswordIsWrong_thenThrowsIllegalStateException() {
    // Given
    ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("wrong", "newPass", "newPass");

    UserDetails userDetails = mock(UserDetails.class);
    lenient().when(userDetails.getUsername()).thenReturn("john@example.com");

    User user = mock(User.class);
    lenient().when(user.getPassword()).thenReturn("current");

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, "wrong", userDetails.getAuthorities());

    when(passwordEncoder.matches("wrong", "current")).thenReturn(false);

    // When / Then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> userService.changePassword(request, authentication));

    verify(user, never()).setPassword(anyString());
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void changePassword_whenNewPasswordsDoNotMatch_thenThrowsIllegalStateException() {
    // Given
    ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("current", "newPass", "different");

    UserDetails userDetails = mock(UserDetails.class);
    lenient().when(userDetails.getUsername()).thenReturn("john@example.com");

    User user = mock(User.class);
    lenient().when(user.getPassword()).thenReturn("current");

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, "current", userDetails.getAuthorities());

    when(passwordEncoder.matches("current", "current")).thenReturn(true);

    // When / Then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> userService.changePassword(request, authentication));

    verify(user, never()).setPassword(anyString());
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void changePassword_whenValidInput_thenUpdatesPasswordSuccessfully() {
    // Given
    ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("current", "newPass", "newPass");

    UserDetails userDetails = mock(UserDetails.class);
    lenient().when(userDetails.getUsername()).thenReturn("john@example.com");

    User user = mock(User.class);
    lenient().when(user.getPassword()).thenReturn("current");

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, "current", userDetails.getAuthorities());

    when(passwordEncoder.matches("current", "current")).thenReturn(true);
    when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");

    // When
    userService.changePassword(request, authentication);

    // Then
    verify(passwordEncoder).encode("newPass");
    verify(user).setPassword("encodedNewPass");
    verify(userRepository).save(user);
  }
}
