package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "marathon")
public class Marathon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String date;

  @ManyToOne
  @JoinColumn(name = "organization_id")
  @JsonBackReference
  private Organization organization;

  @OneToMany(mappedBy = "marathon", cascade = CascadeType.ALL)
  private List<MemberMarathon> memberMarathons;

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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public List<MemberMarathon> getMemberMarathons() {
    return memberMarathons;
  }

  public void setMemberMarathons(List<MemberMarathon> memberMarathons) {
    this.memberMarathons = memberMarathons;
  }
}