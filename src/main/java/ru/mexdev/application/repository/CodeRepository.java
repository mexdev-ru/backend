package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mexdev.application.entity.Code;

import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {
}
