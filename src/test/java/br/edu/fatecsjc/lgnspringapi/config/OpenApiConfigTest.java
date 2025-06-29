
package br.edu.fatecsjc.lgnspringapi.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiConfigTest {

  @Test
  void testInstance() {
    OpenApiConfig config = new OpenApiConfig();
    assertNotNull(config);
  }
}
