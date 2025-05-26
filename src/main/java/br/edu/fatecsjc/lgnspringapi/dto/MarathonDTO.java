package br.edu.fatecsjc.lgnspringapi.dto;

public class MarathonDTO {

  private Long id;
  private String name;
  private String date;
  private Long organizationId;
  private String organizationName;

  public MarathonDTO() {
  }

  public MarathonDTO(Long id, String name, String date, Long organizationId, String organizationName) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.organizationId = organizationId;
    this.organizationName = organizationName;
  }

  

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }
}