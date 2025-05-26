package br.edu.fatecsjc.lgnspringapi.mapper;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;

public class MarathonMapper {

  public static MarathonDTO toDTO(Marathon marathon) {
    if (marathon == null)
      return null;
    return new MarathonDTO(
        marathon.getId(),
        marathon.getName(),
        marathon.getDate(),
        marathon.getOrganization() != null ? marathon.getOrganization().getId() : null,
        marathon.getOrganization() != null ? marathon.getOrganization().getName() : null);
  }

  public static Marathon toEntity(MarathonDTO dto, Organization organization) {
    if (dto == null)
      return null;
    Marathon marathon = new Marathon();
    marathon.setId(dto.getId());
    marathon.setName(dto.getName());
    marathon.setDate(dto.getDate());
    marathon.setOrganization(organization);
    return marathon;
  }
}