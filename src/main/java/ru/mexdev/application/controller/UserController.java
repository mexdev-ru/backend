package ru.mexdev.application.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.service.KeycloakAdminClientService;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("user")
public class UserController {

  @Autowired
  private KeycloakAdminClientService kcAdminClient;

  @RequestMapping(value = "", method = RequestMethod.POST)
  public UserRepresentation createUser(@RequestBody User user) {
    return kcAdminClient.addUser(user);
  }
}
