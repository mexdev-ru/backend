package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Code;
import ru.mexdev.application.repository.CodeRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class CodeService {

  @Autowired
  private CodeRepository repository;

  public void create(@Valid Code element) {
    repository.save(element);
  }

  public List<Code> readAll() {
    return repository.findAll();
  }

  public Code read(UUID id) {
    return repository.findById(id).orElse(null);
  }

  public boolean update(@Valid Code element, UUID id) {
    if (repository.existsById(id)) {
      element.setUuid(id);
      repository.save(element);
      return true;
    }
    return false;
  }

  public boolean delete(UUID id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}
