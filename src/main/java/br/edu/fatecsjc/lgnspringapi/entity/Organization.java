package br.edu.fatecsjc.lgnspringapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}