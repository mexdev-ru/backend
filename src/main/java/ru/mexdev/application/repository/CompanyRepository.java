package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mexdev.application.entity.Company;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
