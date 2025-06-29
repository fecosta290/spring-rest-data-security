package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.ChangePasswordRequestDTO;
import br.edu.fatecsjc.lgnspringapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserResourceTest {

  private UserService service;
  private UserResource resource;

  @BeforeEach
  void setUp() {
    service = mock(UserService.class);
    resource = new UserResource(service);
  }

  @Test
  @DisplayName("Deve alterar a senha e retornar status 200")
  void shouldChangePassword() {
    ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
        .currentPassword("oldPass")
        .newPassword("newPass")
        .confirmationPassword("newPass")
        .build();

    Principal connectedUser = mock(Principal.class);

    ResponseEntity<Void> response = resource.changePassword(request, connectedUser);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(null, response.getBody());

    verify(service, times(1)).changePassword(request, connectedUser);
  }
}