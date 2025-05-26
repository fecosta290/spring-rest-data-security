package br.edu.fatecsjc.lgnspringapi.mapper;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrganizationMapper {

  public static OrganizationDTO toDTO(Organization organization) {
    if (organization == null)
      return null;

    return new OrganizationDTO(
        organization.getId(),
        organization.getName(),
        organization.getNumber(),
        organization.getStreet(),
        organization.getNeighborhood(),
        organization.getCep(),
        organization.getMunicipality(),
        organization.getState(),
        organization.getMarathons() != null
            ? organization.getMarathons().stream()
                .map(MarathonMapper::toDTO)
                .collect(Collectors.toList())
            : Collections.emptyList());
  }

  public static Organization toEntity(OrganizationDTO dto) {
    if (dto == null)
      return null;
    Organization organization = new Organization();
    organization.setId(dto.getId());
    organization.setName(dto.getName());
    organization.setNumber(dto.getNumber());
    organization.setStreet(dto.getStreet());
    organization.setNeighborhood(dto.getNeighborhood());
    organization.setCep(dto.getCep());
    organization.setMunicipality(dto.getMunicipality());
    organization.setState(dto.getState());
    // ⚠️ normalmente não setamos marathons aqui
    return organization;
  }
}