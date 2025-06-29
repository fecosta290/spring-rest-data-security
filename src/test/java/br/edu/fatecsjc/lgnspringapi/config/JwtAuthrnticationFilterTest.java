package br.edu.fatecsjc.lgnspringapi.config;

import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

  @Mock
  private JwtService jwtService;

  @Mock
  private UserDetailsService userDetailsService;

  @Mock
  private TokenRepository tokenRepository;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @InjectMocks
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    SecurityContextHolder.clearContext();
  }

  @Test
  void shouldSkipAuthEndpoints() throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("/auth/login");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void shouldSkipWhenNoAuthHeader() throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("/api/test");
    when(request.getHeader("Authorization")).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void shouldSkipWhenAuthHeaderInvalid() throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("/api/test");
    when(request.getHeader("Authorization")).thenReturn("InvalidToken");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void shouldSetAuthenticationWhenTokenValid() throws ServletException, IOException {
    String token = "validToken";
    String userEmail = "user@example.com";

    when(request.getServletPath()).thenReturn("/api/test");
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtService.extractUsername(token)).thenReturn(userEmail);

    UserDetails userDetails = User.builder()
        .username(userEmail)
        .password("password")
        .roles("USER")
        .build();

    when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
    when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);
    when(tokenRepository.findByToken(token))
        .thenReturn(Optional.of(br.edu.fatecsjc.lgnspringapi.entity.Token.builder()
            .expired(false)
            .revoked(false)
            .build()));

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals(userEmail, SecurityContextHolder.getContext().getAuthentication().getName());
  }

  @Test
  void shouldNotAuthenticateWhenTokenIsInvalid() throws ServletException, IOException {
    String token = "invalidToken";
    String userEmail = "user@example.com";

    when(request.getServletPath()).thenReturn("/api/test");
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtService.extractUsername(token)).thenReturn(userEmail);

    UserDetails userDetails = User.builder()
        .username(userEmail)
        .password("password")
        .roles("USER")
        .build();

    when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
    when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);
    when(tokenRepository.findByToken(token))
        .thenReturn(Optional.of(br.edu.fatecsjc.lgnspringapi.entity.Token.builder()
            .expired(false)
            .revoked(false)
            .build()));

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }
}