/*
package ru.mexdev.application.service;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.*;
import ru.mexdev.application.repository.*;

import java.util.List;
import java.util.UUID;

@Service
public class FileService {









  public FileLink read(UUID id) { return fileRepository.findById(id).orElse(null);  }

  public boolean update(FileLink fileLink, UUID id) {
    if (fileRepository.existsById(id)) {
      fileLink.setUuid(id);
      fileRepository.save(fileLink);
      return true;
    }
    return false;
  }

  public boolean delete(UUID id) {
    if (fileRepository.existsById(id)) {
      fileRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
*/
