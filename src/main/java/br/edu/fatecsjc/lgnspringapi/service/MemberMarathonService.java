package br.edu.fatecsjc.lgnspringapi.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.edu.fatecsjc.lgnspringapi.entity.MemberMarathon;
import br.edu.fatecsjc.lgnspringapi.repository.MemberMarathonRepository;

@Service
public class MemberMarathonService {

    private final MemberMarathonRepository repository;

    public MemberMarathonService(MemberMarathonRepository repository) {
        this.repository = repository;
    }

    public List<MemberMarathon> findAll() {
        return repository.findAll();
    }

    public Optional<MemberMarathon> findById(Long id) {
        return repository.findById(id);
    }

    public MemberMarathon save(MemberMarathon memberMarathon) {
        return repository.save(memberMarathon);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}