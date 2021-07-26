package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoleInCompany {
    @Id
    @Column(name = "uuid")
    @GeneratedValue
    private UUID uuid;

    @Column(name = "company_uuid")
    private UUID companyUuid;

    @Column(name = "role")
    private EmployeeRole role;

    @Autowired
    public RoleInCompany() {
    }

    @Autowired
    public RoleInCompany(UUID companyUuid, EmployeeRole role) {
        this.companyUuid = companyUuid;
        this.role = role;
    }

    public UUID getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(UUID companyUuid) {
        this.companyUuid = companyUuid;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "RoleInCompany{" +
                "uuid=" + uuid +
                ", companyUuid=" + companyUuid +
                ", role='" + role + '\'' +
                '}';
    }
}
