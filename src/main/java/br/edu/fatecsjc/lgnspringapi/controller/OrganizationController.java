package br.edu.fatecsjc.lgnspringapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.mapper.OrganizationMapper;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

  private final OrganizationService service;

  public OrganizationController(OrganizationService service) {
    this.service = service;
  }

  @GetMapping
  public List<OrganizationDTO> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrganizationDTO> getById(@PathVariable Long id) {
    Optional<Organization> organizationOpt = service.findById(id);
    if (organizationOpt.isPresent()) {
      return ResponseEntity.ok(OrganizationMapper.toDTO(organizationOpt.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public OrganizationDTO create(@RequestBody Organization organization) {
    return service.save(organization);
  }

  @PutMapping("/{id}")
  public OrganizationDTO update(@PathVariable Long id, @RequestBody Organization organization) {
    organization.setId(id);
    return service.save(organization);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.deleteById(id);
  }
}