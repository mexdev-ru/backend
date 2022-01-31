package ru.mexdev.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mexdev.application.entity.QuotaLicense;
import ru.mexdev.application.entity.QuotaUsage;
import ru.mexdev.application.entity.Role;
import ru.mexdev.application.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuotaLicenseRepository extends JpaRepository<QuotaLicense, UUID> {
    Optional<QuotaLicense> findQuotaLicenseByUser(User user);
}
