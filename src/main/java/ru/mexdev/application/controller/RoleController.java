package ru.mexdev.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
  
  @Autowired
  private RoleService roleService;

  @GetMapping("/{id}")
  public ResponseEntity<Role> read(@PathVariable(name = "id") Long id) {
    final Role role = roleService.read(id);

    return role != null
        ? new ResponseEntity<>(role, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Role>> read() {
    final List<Role> users = roleService.readAll();

    return users != null
        ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Role role) {
    return roleService.update(role, id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Role role) {
    roleService.create(role);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
    return roleService.delete(id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
