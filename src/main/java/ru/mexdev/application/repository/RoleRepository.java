package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mexdev.application.entity.RoleInCompany;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleInCompany, UUID> {
}
