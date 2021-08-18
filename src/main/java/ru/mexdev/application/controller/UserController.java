package ru.mexdev.application.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.service.KeycloakAdminClientService;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("user")
public class UserController {

  @Autowired
  private KeycloakAdminClientService kcAdminClient;

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<UserRepresentation> read() {
    return kcAdminClient.readAll();
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public Response.StatusType create(@RequestBody User user) {
    return kcAdminClient.addUser(user);
  }
}
