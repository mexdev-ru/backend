package ru.mexdev.application.service;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.mexdev.application.config.KeycloakConfig;
import ru.mexdev.application.entity.User;

import java.util.Collections;

@Service
public class KeycloakAdminClientService {
  public UserRepresentation addUser(User user) {
    UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

    UserRepresentation kcUser = new UserRepresentation();
    kcUser.setUsername(user.getEmail());
    kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
    kcUser.setFirstName(user.getFirstName());
    kcUser.setLastName(user.getLastName());
    kcUser.setEmail(user.getEmail());
    kcUser.setEnabled(true);
    kcUser.setEmailVerified(false);
    usersResource.create(kcUser);
    return kcUser;
  }

  private static CredentialRepresentation  createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }
}
