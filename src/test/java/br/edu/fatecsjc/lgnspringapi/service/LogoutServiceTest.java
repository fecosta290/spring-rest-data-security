package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

  @Mock
  private TokenRepository tokenRepository;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private Authentication authentication;

  @Captor
  private ArgumentCaptor<Token> tokenCaptor;

  @InjectMocks
  private LogoutService logoutService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    SecurityContextHolder.clearContext();
  }

  @Test
  void logout_withValidToken_shouldRevokeToken() {
    // Arrange
    String jwt = "valid.jwt.token";
    String authHeader = "Bearer " + jwt;
    Token token = new Token();
    token.setToken(jwt);
    token.setExpired(false);
    token.setRevoked(false);

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(token));

    // Act
    logoutService.logout(request, response, authentication);

    // Assert
    verify(tokenRepository).save(tokenCaptor.capture());
    Token savedToken = tokenCaptor.getValue();

    assertTrue(savedToken.isExpired());
    assertTrue(savedToken.isRevoked());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void logout_withNonExistingToken_shouldClearContext() {
    // Arrange
    String jwt = "non.existing.token";
    String authHeader = "Bearer " + jwt;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(tokenRepository.findByToken(jwt)).thenReturn(Optional.empty());

    // Act
    logoutService.logout(request, response, authentication);

    // Assert
    verify(tokenRepository, never()).save(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void logout_withoutAuthorizationHeader_shouldDoNothing() {
    // Arrange
    when(request.getHeader("Authorization")).thenReturn(null);

    // Act
    logoutService.logout(request, response, authentication);

    // Assert
    verify(tokenRepository, never()).findByToken(any());
    verify(tokenRepository, never()).save(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void logout_withInvalidAuthorizationHeader_shouldDoNothing() {
    // Arrange
    when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

    // Act
    logoutService.logout(request, response, authentication);

    // Assert
    verify(tokenRepository, never()).findByToken(any());
    verify(tokenRepository, never()).save(any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }
}