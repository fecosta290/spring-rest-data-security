package br.edu.fatecsjc.lgnspringapi;

import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationRequestDTO;
import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationResponseDTO;
import br.edu.fatecsjc.lgnspringapi.dto.RegisterRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import br.edu.fatecsjc.lgnspringapi.service.AuthenticationService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

// Add import for JwtService
import br.edu.fatecsjc.lgnspringapi.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private TokenRepository tokenRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private ServletOutputStream outputStream;

  @InjectMocks
  private AuthenticationService authenticationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRegister_ShouldReturnAuthenticationResponse() {
    RegisterRequestDTO request = RegisterRequestDTO.builder()
        .firstname("John")
        .lastname("Doe")
        .email("john@example.com")
        .password("password")
        .role(Role.USER)
        .build();

    User user = User.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john@example.com")
        .password("encodedPassword")
        .role(Role.USER)
        .build();

    when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(jwtService.generateToken(user)).thenReturn("access-token");
    when(jwtService.generateRefreshToken(user)).thenReturn("refresh-token");

    AuthenticationResponseDTO response = authenticationService.register(request);

    assertEquals("access-token", response.getAccessToken());
    assertEquals("refresh-token", response.getRefreshToken());
    verify(tokenRepository).save(any());
  }

  @Test
  void testAuthenticate_ShouldReturnAuthenticationResponse() {
    AuthenticationRequestDTO request = AuthenticationRequestDTO.builder()
        .email("john@example.com")
        .password("password")
        .build();

    User user = User.builder()
        .id(1L)
        .email("john@example.com")
        .password("encodedPassword")
        .role(Role.USER)
        .build();

    when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
    when(jwtService.generateToken(user)).thenReturn("access-token");
    when(jwtService.generateRefreshToken(user)).thenReturn("refresh-token");
    when(tokenRepository.findAllValidTokenByUser(1L)).thenReturn(List.of());

    AuthenticationResponseDTO response = authenticationService.authenticate(request);

    assertEquals("access-token", response.getAccessToken());
    assertEquals("refresh-token", response.getRefreshToken());

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(tokenRepository).save(any());
  }

  @Test
  void testAuthenticate_ShouldRevokeTokens() {
    AuthenticationRequestDTO request = AuthenticationRequestDTO.builder()
        .email("john@example.com")
        .password("password")
        .build();

    Token token1 = Token.builder().revoked(false).expired(false).build();
    Token token2 = Token.builder().revoked(false).expired(false).build();

    User user = User.builder()
        .id(1L)
        .email("john@example.com")
        .password("encodedPassword")
        .role(Role.USER)
        .build();

    when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
    when(jwtService.generateToken(user)).thenReturn("access-token");
    when(jwtService.generateRefreshToken(user)).thenReturn("refresh-token");
    when(tokenRepository.findAllValidTokenByUser(1L)).thenReturn(List.of(token1, token2));

    authenticationService.authenticate(request);

    verify(tokenRepository).saveAll(argThat(tokens -> StreamSupport.stream(tokens.spliterator(), false)
        .allMatch(t -> t.isRevoked() && t.isExpired())));

  }

  @Test
  void testRefreshToken_ShouldRefreshIfValid() throws IOException {
    String refreshToken = "valid-refresh-token";
    String email = "john@example.com";
    String accessToken = "new-access-token";

    User user = User.builder()
        .id(1L)
        .email(email)
        .role(Role.USER)
        .build();

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
    when(jwtService.extractUsername(refreshToken)).thenReturn(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
    when(jwtService.generateToken(user)).thenReturn(accessToken);
    when(response.getOutputStream()).thenReturn(outputStream);
    when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(List.of());

    authenticationService.refreshToken(request, response);

    verify(response).getOutputStream();
    verify(tokenRepository).save(any());
  }

  @Test
  void testRefreshToken_ShouldReturnWhenInvalidHeader() throws IOException {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    authenticationService.refreshToken(request, response);

    verifyNoInteractions(userRepository);
    verifyNoInteractions(jwtService);
    verifyNoInteractions(response);
  }

  @Test
  void testRefreshToken_ShouldReturnWhenTokenInvalid() throws IOException {
    String refreshToken = "invalid-token";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
    when(jwtService.extractUsername(refreshToken)).thenReturn(null);

    authenticationService.refreshToken(request, response);

    verifyNoInteractions(userRepository);
    verifyNoInteractions(response);
  }

  @Test
  void testRefreshToken_ShouldNotProceedWhenTokenNotValid() throws IOException {
    String refreshToken = "refresh-token";
    String email = "john@example.com";
    User user = User.builder().email(email).build();

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
    when(jwtService.extractUsername(refreshToken)).thenReturn(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(jwtService.isTokenValid(refreshToken, user)).thenReturn(false);

    authenticationService.refreshToken(request, response);

    verify(tokenRepository, never()).save(any());
    verify(response, never()).getOutputStream();
  }

  @Test
  void testAuthenticate_ThrowsWhenUserNotFound() {
    AuthenticationRequestDTO request = AuthenticationRequestDTO.builder()
        .email("notfound@example.com")
        .password("123")
        .build();

    when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> authenticationService.authenticate(request));
  }
}
