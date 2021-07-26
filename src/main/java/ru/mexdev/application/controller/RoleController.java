package ru.mexdev.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<RoleInCompany> read(@PathVariable(name = "id") UUID id) {
        final RoleInCompany role = roleService.read(id);

        return role != null
                ? new ResponseEntity<>(role, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleInCompany>> read() {
        final List<RoleInCompany> roles = roleService.readAll();

        return roles != null
                ? new ResponseEntity<>(roles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody RoleInCompany role) {
        return roleService.update(role, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleInCompany role) {
        return roleService.create(role)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        return roleService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
