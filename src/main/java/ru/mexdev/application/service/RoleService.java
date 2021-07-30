package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

  @Autowired
  private RoleRepository repository;

  public boolean create(Role element) {
    if (!repository.existsByName(element.getName())) {
      repository.save(element);
      return true;
    }
    return false;
  }

  public List<Role> readAll() {
    return repository.findAll();
  }

  public Role read(Long id) {
    return repository.findById(id).orElse(null);
  }

  public boolean update(Role element, Long id) {
    if (repository.existsById(id)) {
      element.setId(id);
      repository.save(element);
      return true;
    }
    return false;
  }

  public boolean delete(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}
