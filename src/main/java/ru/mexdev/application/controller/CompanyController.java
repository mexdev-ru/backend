package ru.mexdev.application.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.Company;
import ru.mexdev.application.service.CompanyService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("company")
public class CompanyController {

  @Autowired
  private CompanyService companyService;

  @RequestMapping(method = RequestMethod.GET, path = "/{id}")
  public ResponseEntity<Company> read(@PathVariable(name = "id") UUID id) {
    final Company company = companyService.read(id);

    return company != null
        ? new ResponseEntity<>(company, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all")
  public ResponseEntity<List<Company>> read() {
    final List<Company> users = companyService.readAll();

    return users != null
        ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody Company company) {
    return companyService.update(company, id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody Company company, KeycloakAuthenticationToken authentication) {
    return companyService.create(company, authentication)
        ? new ResponseEntity<>(HttpStatus.CREATED)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return companyService.delete(id)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
