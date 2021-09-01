package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  Optional<Employee> findByUserIdUuid(UUID userId_uuid);

  boolean existsByUserId(User user);
}
