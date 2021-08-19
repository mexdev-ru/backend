package ru.mexdev.application.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.service.KeycloakAdminClientService;
import ru.mexdev.application.service.UserService;

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
  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.GET, path = "/keycloak/{id}")
  public ResponseEntity<UserRepresentation> readkeycloak(@PathVariable(name = "id") UUID id) {
    try {
      return new ResponseEntity<>(kcAdminClient.read(id), HttpStatus.OK);
    }
    catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{id}")
  public ResponseEntity<User> read(@PathVariable(name = "id") UUID id) {
    final User user = userService.read(id);

    return user != null
            ? new ResponseEntity<>(user, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/keycloak/all")
  public ResponseEntity<List<UserRepresentation>> readKeycloak() {
    final List<UserRepresentation> users = kcAdminClient.readAll();

    return users != null
        ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all")
  public ResponseEntity<List<User>> read() {
    final List<User> users = userService.readAll();

    return users != null
            ? new ResponseEntity<>(users, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody User user) {
    if (userService.update(user, id)) {
      try {
        kcAdminClient.update(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      catch (ClientErrorException e) {
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

 //////////TO DO//////////////different uuid (keycloak!=userService)
  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody User user) {
    userService.create(user);
    if((HttpStatus.valueOf(kcAdminClient.create(user))).equals(HttpStatus.CREATED)) {
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    else return new ResponseEntity<>(HttpStatus.valueOf(kcAdminClient.create(user)));
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return userService.delete(id)
            ? new ResponseEntity<>(HttpStatus.valueOf(kcAdminClient.delete(id)))
            : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
