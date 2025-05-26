package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.mapper.OrganizationMapper;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;

@Service
public class OrganizationService {

  private final OrganizationRepository repository;

  public OrganizationService(OrganizationRepository repository) {
    this.repository = repository;
  }

  public List<OrganizationDTO> findAll() {
    return repository.findAll().stream()
        .map(OrganizationMapper::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<Organization> findById(Long id) {
    return repository.findById(id);
  }

  public OrganizationDTO save(Organization organization) {
    Organization saved = repository.save(organization);
    return OrganizationMapper.toDTO(saved);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}