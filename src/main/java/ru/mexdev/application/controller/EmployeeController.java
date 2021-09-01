package ru.mexdev.application.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

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
  public ResponseEntity<?> addRole(@PathVariable(name = "id") UUID id, @RequestBody RoleInCompany role,
                                   KeycloakAuthenticationToken authentication) {
    return employeeService.addRole(role, id, authentication)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody Employee employee) {
    return employeeService.create(employee)
        ? new ResponseEntity<>(HttpStatus.CREATED)
        : new ResponseEntity<>(HttpStatus.CONFLICT);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return employeeService.delete(id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
