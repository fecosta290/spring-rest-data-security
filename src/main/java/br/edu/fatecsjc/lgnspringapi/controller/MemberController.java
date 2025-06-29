package br.edu.fatecsjc.lgnspringapi.controller;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private GroupRepository groupRepository;

  // 🔹 GET - Listar todos os membros
  @GetMapping
  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  // 🔹 GET - Buscar membro por ID
  @GetMapping("/{id}")
  public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
    Optional<Member> member = memberRepository.findById(id);
    return member.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // 🔹 POST - Criar membro
  @PostMapping
  public ResponseEntity<Member> createMember(@RequestBody Member member) {
    Optional<Group> group = groupRepository.findById(member.getGroup().getId());
    if (group.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    member.setGroup(group.get());
    Member saved = memberRepository.save(member);
    return ResponseEntity.ok(saved);
  }

  // 🔹 PUT - Atualizar membro
  @PutMapping("/{id}")
  public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
    Optional<Member> optionalMember = memberRepository.findById(id);
    if (optionalMember.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Member member = optionalMember.get();
    member.setName(memberDetails.getName());
    member.setAge(memberDetails.getAge());

    // Verificar se o grupo enviado existe
    Optional<Group> group = groupRepository.findById(memberDetails.getGroup().getId());
    if (group.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    member.setGroup(group.get());

    Member updated = memberRepository.save(member);
    return ResponseEntity.ok(updated);
  }

  // 🔹 DELETE - Deletar membro por ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
    Optional<Member> optionalMember = memberRepository.findById(id);
    if (optionalMember.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    memberRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}