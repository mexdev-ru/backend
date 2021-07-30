package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Company;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.CompanyRepository;
import ru.mexdev.application.repository.EmployeeRepository;
import ru.mexdev.application.repository.RoleInCompanyRepository;
import ru.mexdev.application.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

  private final static String ADMIN_ROLE_NAME = "ADMIN";

  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RoleInCompanyRepository roleInCompanyRepository;
  @Autowired
  private RoleRepository roleRepository;

  public boolean create(Company element) {
    if (!companyRepository.existsByName(element.getName())) {
      companyRepository.save(element);

      Employee employee = new Employee();
      employeeRepository.save(employee);
      Role role = roleRepository.findByName(ADMIN_ROLE_NAME).orElse(null);
      if (role == null) {
        role = new Role(ADMIN_ROLE_NAME);
        roleRepository.save(role);
      }
      RoleInCompany roleInCompany = new RoleInCompany(element, role, null, employee);
      roleInCompanyRepository.save(roleInCompany);
      employee.getRoles().add(roleInCompany);
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
