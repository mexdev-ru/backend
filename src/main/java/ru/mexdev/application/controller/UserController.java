package ru.mexdev.application.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.service.KeycloakAdminClientService;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("user")
public class UserController {

  @Autowired
  private KeycloakAdminClientService kcAdminClient;

  @RequestMapping(method = RequestMethod.GET, path = "/{id}")
  public ResponseEntity<UserRepresentation> read(@PathVariable(name = "id") UUID id) {
    try {
      return new ResponseEntity<>(kcAdminClient.read(id), HttpStatus.OK);
    }
    catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all")
  public ResponseEntity<List<UserRepresentation>> read() {
    final List<UserRepresentation> users = kcAdminClient.readAll();

    return users != null
        ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody User user) {
    try {
      kcAdminClient.update(user, id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    catch (ClientErrorException e) {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody User user) {
    return new ResponseEntity<>(HttpStatus.valueOf(kcAdminClient.create(user)));
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return new ResponseEntity<>(HttpStatus.valueOf(kcAdminClient.delete(id)));
  }
}
