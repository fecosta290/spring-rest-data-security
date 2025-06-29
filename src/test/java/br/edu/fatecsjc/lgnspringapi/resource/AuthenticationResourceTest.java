
package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationRequestDTO;
import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationResponseDTO;
import br.edu.fatecsjc.lgnspringapi.dto.RegisterRequestDTO;
import br.edu.fatecsjc.lgnspringapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationResourceTest {

  private AuthenticationService service;
  private AuthenticationResource resource;

  @BeforeEach
  void setUp() {
    service = Mockito.mock(AuthenticationService.class);
    resource = new AuthenticationResource(service);
  }

  @Test
  @DisplayName("Deve registrar um usuário e retornar status 201")
  void shouldRegisterUser() {
    RegisterRequestDTO request = new RegisterRequestDTO();
    AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO("accessToken", "refreshToken");

    when(service.register(request)).thenReturn(responseDTO);

    ResponseEntity<AuthenticationResponseDTO> response = resource.register(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("accessToken", response.getBody().getAccessToken());
    assertEquals("refreshToken", response.getBody().getRefreshToken());
    verify(service, times(1)).register(request);
  }

  @Test
  @DisplayName("Deve autenticar um usuário e retornar status 200")
  void shouldAuthenticateUser() {
    AuthenticationRequestDTO request = new AuthenticationRequestDTO();
    AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO("accessToken", "refreshToken");

    when(service.authenticate(request)).thenReturn(responseDTO);

    ResponseEntity<AuthenticationResponseDTO> response = resource.authenticate(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("accessToken", response.getBody().getAccessToken());
    assertEquals("refreshToken", response.getBody().getRefreshToken());
    verify(service, times(1)).authenticate(request);
  }

  @Test
  @DisplayName("Deve chamar refreshToken e retornar status 200")
  void shouldRefreshToken() throws IOException {
    HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    HttpServletResponse httpResponse = mock(HttpServletResponse.class);

    ResponseEntity<Void> response = resource.refreshToken(httpRequest, httpResponse);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
    verify(service, times(1)).refreshToken(httpRequest, httpResponse);
  }
}
