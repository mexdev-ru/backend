package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.UuidIdentification;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateService<T extends UuidIdentification> {

    @Autowired
    private JpaRepository<T, UUID> repository;

    public void create(T element) {
        repository.save(element);
    }

    public List<T> readAll() {
        return repository.findAll();
    }

    public T read(UUID id) {
        return repository.getById(id);
    }

    public boolean update(T element, UUID id) {
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
