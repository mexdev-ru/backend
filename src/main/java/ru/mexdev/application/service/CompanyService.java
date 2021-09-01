package ru.mexdev.application.service;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Company;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.*;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

  protected final static String COMPANY_GENERAL_ROLE_NAME = "ADMIN";

  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RoleInCompanyRepository roleInCompanyRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;

  public boolean create(Company element, KeycloakAuthenticationToken authentication) {
    if (!companyRepository.existsByName(element.getName())) {
      companyRepository.save(element);

      Employee employee = new Employee();
      employeeRepository.save(employee);
      Role role = roleRepository.findByName(COMPANY_GENERAL_ROLE_NAME).orElse(null);
      if (role == null) {
        role = new Role(COMPANY_GENERAL_ROLE_NAME);
        roleRepository.save(role);
      }
      RoleInCompany roleInCompany = new RoleInCompany(element, role, null, employee);
      roleInCompanyRepository.save(roleInCompany);
      employee.getRoles().add(roleInCompany);
      employee.setUserId(userRepository.findById(UUID.fromString(authentication.getPrincipal().toString())).orElse(null));
      employeeRepository.save(employee);
      return true;
    }
    return false;
  }

  public List<Company> readAll() {
    return companyRepository.findAll();
  }

  public Company read(UUID id) {
    return companyRepository.findById(id).orElse(null);
  }

  public boolean update(Company element, UUID id) {
    if (companyRepository.existsById(id)) {
      element.setUuid(id);
      companyRepository.save(element);
      return true;
    }
    return false;
  }

  public boolean delete(UUID id) {
    if (companyRepository.existsById(id)) {
      companyRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
