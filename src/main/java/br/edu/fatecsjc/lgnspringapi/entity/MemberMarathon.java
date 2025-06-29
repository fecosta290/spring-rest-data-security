package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "member_marathon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMarathon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Long time;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "marathon_id")
  private Marathon marathon;
}