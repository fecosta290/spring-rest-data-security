package br.edu.fatecsjc.lgnspringapi.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "member_marathon")
public class MemberMarathon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "marathon_id")
  private Marathon marathon;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Marathon getMarathon() {
    return marathon;
  }

  public void setMarathon(Marathon marathon) {
    this.marathon = marathon;
  }
}