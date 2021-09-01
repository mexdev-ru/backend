package ru.mexdev.application.service;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.EmployeeRepository;

import java.util.List;
import java.util.UUID;

import static ru.mexdev.application.service.CompanyService.COMPANY_GENERAL_ROLE_NAME;

@Service
public class EmployeeService {

  private static boolean checkAccess(Employee employee, String requiredRole) {
    return employee.getRoles().stream().anyMatch(roleInCompany -> roleInCompany.getRole().getName().equals(requiredRole));
  }

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private RoleInCompanyService roleInCompanyService;

  public boolean create(Employee element) {
    if (employeeRepository.existsByUserId(element.getUserId())) {
      return false;
    }
    employeeRepository.save(element);
    return true;
  }

  public List<Employee> readAll() {
    return employeeRepository.findAll();
  }

  public Employee read(UUID id) {
    return employeeRepository.findById(id).orElse(null);
  }

  public boolean update(Employee element, UUID id) {
    if (!employeeRepository.existsById(id)) {
      return false;
    }
    element.setUuid(id);
    employeeRepository.save(element);
    return true;
  }

  public boolean delete(UUID id) {
    if (!employeeRepository.existsById(id)) {
      return false;
    }
    employeeRepository.deleteById(id);
    return true;
  }

  public boolean addRole(RoleInCompany role, UUID id, KeycloakAuthenticationToken authentication) {

    Employee authEmployee = employeeRepository.findByUserIdUuid(
        UUID.fromString(authentication.getPrincipal().toString())).orElse(null);

    Employee employee = employeeRepository.findById(id).orElse(null);
    role.setRoleIssuer(authEmployee);
    role.setEmployee(employee);

    if (authEmployee != null && employee != null && checkAccess(authEmployee, COMPANY_GENERAL_ROLE_NAME)
        && roleInCompanyService.create(role)) {
      employee.getRoles().add(role);
      employeeRepository.save(employee);
      return true;
    }
    return false;
  }
}
