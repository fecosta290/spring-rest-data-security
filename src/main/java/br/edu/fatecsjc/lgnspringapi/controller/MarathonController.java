package br.edu.fatecsjc.lgnspringapi.controller;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marathons")
public class MarathonController {

  private final MarathonService service;
  private final OrganizationService organizationService;

  public MarathonController(MarathonService service, OrganizationService organizationService) {
    this.service = service;
    this.organizationService = organizationService;
  }

  @GetMapping
  public List<MarathonDTO> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public MarathonDTO getById(@PathVariable Long id) {
    MarathonDTO marathonDTO = service.findById(id);
    if (marathonDTO == null) {
      throw new RuntimeException("Marathon not found");
    }
    return marathonDTO;
  }

  @PostMapping
  public MarathonDTO create(@RequestBody MarathonRequestDTO request) {
    Organization organization = organizationService.findById(request.getOrganizationId())
        .orElseThrow(() -> new RuntimeException("Organization not found"));

    Marathon marathon = new Marathon();
    marathon.setName(request.getName());
    marathon.setDate(request.getDate());
    marathon.setOrganization(organization);

    return service.save(marathon);
  }

  @PutMapping("/{id}")
  public MarathonDTO update(@PathVariable Long id, @RequestBody MarathonRequestDTO request) {
    Marathon existing = service.findById(id) != null
        ? service.findAll().stream().filter(m -> m.getId().equals(id)).findFirst().map(dto -> {
          Marathon m = new Marathon();
          m.setId(dto.getId());
          m.setName(dto.getName());
          m.setDate(dto.getDate());
          // você pode querer carregar a entidade organizacao para setar aqui
          return m;
        }).orElse(null)
        : null;

    if (existing == null) {
      throw new RuntimeException("Marathon not found");
    }

    existing.setName(request.getName());
    existing.setDate(request.getDate());

    if (request.getOrganizationId() != null) {
      Organization organization = organizationService.findById(request.getOrganizationId())
          .orElseThrow(() -> new RuntimeException("Organization not found"));
      existing.setOrganization(organization);
    }

    return service.save(existing);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.deleteById(id);
  }
}