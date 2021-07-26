package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.User;
import ru.mexdev.application.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void create(User element) {
        repository.save(element);
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public User read(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public boolean update(User element, UUID id) {
        if (repository.existsById(id)) {
            element.setUuid(id);
            repository.save(element);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
