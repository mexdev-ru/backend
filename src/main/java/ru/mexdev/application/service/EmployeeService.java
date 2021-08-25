package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.EmployeeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RoleInCompanyService roleInCompanyService;

  public void create(Employee element) {
    employeeRepository.save(element);
  }

  public List<Employee> readAll() {
    return employeeRepository.findAll();
  }

  public Employee read(UUID id) {
    return employeeRepository.findById(id).orElse(null);
  }

  public boolean update(Employee element, UUID id) {
    if (employeeRepository.existsById(id)) {
      element.setUuid(id);
      employeeRepository.save(element);
      return true;
    }
    return false;
  }

  public boolean delete(UUID id) {
    if (employeeRepository.existsById(id)) {
      employeeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean addRole(RoleInCompany role, UUID id) {
    Employee employee = employeeRepository.findById(id).orElse(null);
    if (employee != null && roleInCompanyService.create(role)) {
      role.setEmployee(employee);
      employee.getRoles().add(role);
      employeeRepository.save(employee);
      return true;
    }
    return false;
  }

  public UUID searchByUserId(UUID userUuid) {
    return employeeRepository.findByUserId(userUuid);
  }

  public boolean checkAccess(UUID employeUuid, String requiredRole) {
    Employee employee = employeeRepository.getById(employeUuid);
    List<RoleInCompany> listRolesInCompany = employee.getRoles();
    List<Role> listRoles = null;
    listRolesInCompany.forEach(roleInCompany -> listRoles.add(roleInCompany.getRole()));
    List<String> listRoleName = null;
    listRoles.forEach(role -> listRoleName.add(role.getName()));
    if(listRoleName.contains(requiredRole)){ return true;}
    else {return false;}
  }

}
