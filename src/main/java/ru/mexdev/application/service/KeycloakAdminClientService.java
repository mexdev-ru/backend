package ru.mexdev.application.service;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.mexdev.application.config.KeycloakConfig;
import ru.mexdev.application.entity.User;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class KeycloakAdminClientService {

  private static CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }

  private static void updateKcUser(User user, UserRepresentation kcUser) {
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
    kcUser.setUsername(user.getEmail());
    kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
    kcUser.setFirstName(user.getFirstName());
    kcUser.setLastName(user.getLastName());
    kcUser.setEmail(user.getEmail());
  }

  public int create(User user) {
    UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    UserRepresentation kcUser = new UserRepresentation();
    updateKcUser(user, kcUser);
    kcUser.setEnabled(true);
    kcUser.setEmailVerified(false);
    return usersResource.create(kcUser).getStatus();
  }

  public List<UserRepresentation> readAll() {
    return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().list();
  }

  public UserRepresentation read(UUID id) throws NotFoundException {
    return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().get(id.toString()).toRepresentation();
  }

  public void update(User element, UUID id) throws ClientErrorException {
    UserResource userResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().get(id.toString());
    UserRepresentation kcUser = userResource.toRepresentation();
    updateKcUser(element, kcUser);
    userResource.update(kcUser);
  }

  public int delete(UUID id) {
    return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().delete(id.toString()).getStatus();
  }
}
