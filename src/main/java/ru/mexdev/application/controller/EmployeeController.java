package ru.mexdev.application.controller;

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
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> read(@PathVariable(name = "id") UUID id) {
        final Employee employee = employeeService.read(id);

        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> read() {
        final List<Employee> roles = employeeService.readAll();

        return roles != null
                ? new ResponseEntity<>(roles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody Employee employee) {
        return employeeService.update(employee, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/add_role/{id}")
    public ResponseEntity<?> addRole(@PathVariable(name = "id") UUID id, @RequestBody RoleInCompany role) {
        return employeeService.addRole(role, id)
            ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        return employeeService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
