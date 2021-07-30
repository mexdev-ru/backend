package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.EmployeeRepository;
import ru.mexdev.application.repository.RoleInCompanyRepository;
import ru.mexdev.application.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RoleInCompanyService {

  @Autowired
  private RoleInCompanyRepository roleInCompanyRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RoleRepository roleRepository;

  public boolean create(RoleInCompany element) {
    Employee employeeIssuer = employeeRepository.findById(element.getRoleIssuer().getUuid()).orElse(null);
    if (employeeIssuer != null
        && roleRepository.existsById(element.getRole().getId())
        && employeeIssuer.getRoles().stream()
        .anyMatch(role -> role.getCompany().getUuid().equals(element.getCompany().getUuid()))) {

      roleInCompanyRepository.save(element);
      return true;
    }
    return false;
  }

  public List<RoleInCompany> readAll() {
    return roleInCompanyRepository.findAll();
  }

  public RoleInCompany read(UUID id) {
    return roleInCompanyRepository.findById(id).orElse(null);
  }

  public boolean update(RoleInCompany element, UUID id) {
    if (roleInCompanyRepository.existsById(id)) {
      element.setUuid(id);
      roleInCompanyRepository.save(element);
      return true;
    }
    return false;
  }

  public boolean delete(UUID id) {
    if (roleInCompanyRepository.existsById(id)) {
      roleInCompanyRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
