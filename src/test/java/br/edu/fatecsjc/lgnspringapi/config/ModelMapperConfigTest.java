
package br.edu.fatecsjc.lgnspringapi.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NamingConventions;

import static org.junit.jupiter.api.Assertions.*;

class ModelMapperConfigTest {

  @Test
  void testModelMapperBeanCreation() {
    ModelMapperConfig config = new ModelMapperConfig();
    ModelMapper modelMapper = config.modelMapper();

    assertNotNull(modelMapper, "ModelMapper should not be null");

    Configuration configuration = modelMapper.getConfiguration();
    assertTrue(configuration.isFieldMatchingEnabled(), "Field matching should be enabled");
    assertEquals(
        org.modelmapper.config.Configuration.AccessLevel.PRIVATE,
        configuration.getFieldAccessLevel(),
        "Field access level should be PRIVATE");
    assertEquals(
        NamingConventions.JAVABEANS_MUTATOR,
        configuration.getSourceNamingConvention(),
        "Source naming convention should be JAVABEANS_MUTATOR");
  }
}
