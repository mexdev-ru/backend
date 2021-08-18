package ru.mexdev.application.service;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.mexdev.application.config.KeycloakConfig;
import ru.mexdev.application.entity.User;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class KeycloakAdminClientService {
  public Response.StatusType addUser(User user) {
    UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

    UserRepresentation kcUser = new UserRepresentation();
    kcUser.setId(String.valueOf(UUID.randomUUID()));
    kcUser.setUsername(user.getEmail());
    kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
    kcUser.setFirstName(user.getFirstName());
    kcUser.setLastName(user.getLastName());
    kcUser.setEmail(user.getEmail());
    kcUser.setEnabled(true);
    kcUser.setEmailVerified(false);
    return usersResource.create(kcUser).getStatusInfo();
  }

  public List<UserRepresentation> readAll() {
    return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().list();
  }

  private static CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }
}
