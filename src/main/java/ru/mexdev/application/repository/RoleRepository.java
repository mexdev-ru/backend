package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mexdev.application.entity.RoleInCompany;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleInCompany, UUID> {
}
