package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mexdev.application.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>  {
}