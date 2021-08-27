package ru.mexdev.application.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.UserRepository;
import ru.mexdev.application.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private UserRepository userRepository;

  @RequestMapping(method = RequestMethod.GET, path = "/{id}")
  public ResponseEntity<Employee> read(@PathVariable(name = "id") UUID id) {
    final Employee employee = employeeService.read(id);

    return employee != null
        ? new ResponseEntity<>(employee, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all")
  public ResponseEntity<List<Employee>> read() {
    final List<Employee> roles = employeeService.readAll();

    return roles != null
        ? new ResponseEntity<>(roles, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody Employee employee) {
    return employeeService.update(employee, id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/add_role/{id}")
  public ResponseEntity<?> addRole(@PathVariable(name = "id") UUID id, @RequestBody RoleInCompany role) {
    return employeeService.addRole(role, id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create( @RequestBody Employee employee, KeycloakAuthenticationToken authentication) {
    Logger.getAnonymousLogger().info(authentication.getPrincipal().toString());

    Logger.getAnonymousLogger().info(String.valueOf(UUID.fromString(authentication.getPrincipal().toString())));
    Employee authEmployee = employeeService.searchByUserId(UUID.fromString(authentication.getPrincipal().toString()));
    //Employee employee = new Employee();
    //employeee.setRoles();
    /*Role role = new Role();
    role.setName("ADMIN");
    role.setId((long) 1);
    RoleInCompany roleInCompany = new RoleInCompany();
    roleInCompany.setRole(role);
    List<RoleInCompany>listRoleInCompany = new ArrayList<>();
    listRoleInCompany.add(roleInCompany);
    authEmployee.setRoles(listRoleInCompany);
    employee.setUserId(userRepository.findById(UUID.fromString(authentication.getPrincipal().toString())).orElse(null));*/
    if (authEmployee != null && employeeService.checkAccess(authEmployee, "ADMIN")) {
      employeeService.create(employee);
      //Logger.getAnonymousLogger().info(employee.getRoles().toString());
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    else {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return employeeService.delete(id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
