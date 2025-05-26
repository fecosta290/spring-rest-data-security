package br.edu.fatecsjc.lgnspringapi.dto;

import java.util.List;

public class OrganizationDTO {

  private Long id;
  private String name;
  private String number;
  private String street;
  private String neighborhood;
  private String cep;
  private String municipality;
  private String state;
  private List<MarathonDTO> marathons; 

  public OrganizationDTO() {
  }

  public OrganizationDTO(Long id, String name, String number, String street, String neighborhood,
      String cep, String municipality, String state, List<MarathonDTO> marathons) {
    this.id = id;
    this.name = name;
    this.number = number;
    this.street = street;
    this.neighborhood = neighborhood;
    this.cep = cep;
    this.municipality = municipality;
    this.state = state;
    this.marathons = marathons;
  }

  // Getters e Setters

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

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public List<MarathonDTO> getMarathons() {
    return marathons;
  }

  public void setMarathons(List<MarathonDTO> marathons) {
    this.marathons = marathons;
  }
}