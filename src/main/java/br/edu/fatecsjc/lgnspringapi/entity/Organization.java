package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "organization")
public class Organization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String number;

  private String street;

  private String neighborhood;

  private String cep;

  private String municipality;

  private String state;

  @Column(name = "institution_name")
  @JsonProperty("institutionName")
  private String institutionName;

  @Column(name = "host_country")
  @JsonProperty("hostCountry")
  private String hostCountry;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Marathon> marathons;

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

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public String getHostCountry() {
    return hostCountry;
  }

  public void setHostCountry(String hostCountry) {
    this.hostCountry = hostCountry;
  }

  public List<Marathon> getMarathons() {
    return marathons;
  }

  public void setMarathons(List<Marathon> marathons) {
    this.marathons = marathons;
  }
}