package br.edu.fatecsjc.lgnspringapi.controller;

import org.springframework.web.bind.annotation.*;

import br.edu.fatecsjc.lgnspringapi.entity.MemberMarathon;
import br.edu.fatecsjc.lgnspringapi.service.MemberMarathonService;

import java.util.List;

@RestController
@RequestMapping("/api/member-marathons")
public class MemberMarathonController {

  private final MemberMarathonService service;

  public MemberMarathonController(MemberMarathonService service) {
    this.service = service;
  }

  @GetMapping
  public List<MemberMarathon> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public MemberMarathon getById(@PathVariable Long id) {
    return service.findById(id).orElseThrow();
  }

  @PostMapping
  public MemberMarathon create(@RequestBody MemberMarathon memberMarathon) {
    return service.save(memberMarathon);
  }

  @PutMapping("/{id}")
  public MemberMarathon update(@PathVariable Long id, @RequestBody MemberMarathon memberMarathon) {
    memberMarathon.setId(id);
    return service.save(memberMarathon);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.deleteById(id);
  }
}
