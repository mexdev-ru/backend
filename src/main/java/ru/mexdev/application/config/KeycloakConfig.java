package ru.mexdev.application.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakConfig {

  static Keycloak keycloak = null;
  public final static String serverUrl = "http://localhost:8080/auth";
  public final static String realm = "my_realm";
  public final static String clientId = "my_client";
  //final static String clientSecret = "YOUR_CLIENT_SECRET_KEY";
  final static String userName = "admin";
  final static String password = "admin_password";

  public KeycloakConfig() {
  }

  public static Keycloak getInstance() {
    if (keycloak == null) {

      keycloak = KeycloakBuilder.builder()
          .serverUrl(serverUrl)
          .realm(realm)
          .grantType(OAuth2Constants.PASSWORD)
          .username(userName)
          .password(password)
          .clientId(clientId)
          //.clientSecret(clientSecret)
          .resteasyClient(new ResteasyClientBuilder()
              .connectionPoolSize(10)
              .build())
          .build();
    }
    return keycloak;
  }
}
