package br.edu.fatecsjc.lgnspringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "marathon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}