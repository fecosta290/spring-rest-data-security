package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.mapper.MarathonMapper;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;

@Service
public class MarathonService {

  private final MarathonRepository repository;

  public MarathonService(MarathonRepository repository) {
    this.repository = repository;
  }

  public List<MarathonDTO> findAll() {
    return repository.findAll().stream()
        .map(MarathonMapper::toDTO)
        .collect(Collectors.toList());
  }

  public MarathonDTO findById(Long id) {
    return repository.findById(id)
        .map(MarathonMapper::toDTO)
        .orElse(null);
  }

  public MarathonDTO save(Marathon marathon) {
    Marathon saved = repository.save(marathon);
    return MarathonMapper.toDTO(saved);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}