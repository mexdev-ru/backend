package ru.mexdev.application.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.config.KeycloakConfig;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.service.KeycloakAdminClientService;
import ru.mexdev.application.service.UserService;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private KeycloakAdminClientService kcAdminClient;
  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.GET, path = "/keycloak/{id}")
  public ResponseEntity<UserRepresentation> readKeycloak(@PathVariable(name = "id") UUID id) {
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
    }
    else {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody User user) {

    HttpStatus kcUserHttpStatus = HttpStatus.valueOf(kcAdminClient.create(user));
    if (kcUserHttpStatus.equals(HttpStatus.CREATED)) {
      List<UserRepresentation> ur = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users()
              .search(user.getEmail(), user.getFirstName(), user.getLastName(), user.getEmail(), 0, 1);

      user.setUuid(UUID.fromString(ur.get(0).getId()));
      userService.create(user);
    }
    return new ResponseEntity<>(kcUserHttpStatus);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    return userService.delete(id)
        ? new ResponseEntity<>(HttpStatus.valueOf(kcAdminClient.delete(id)))
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
