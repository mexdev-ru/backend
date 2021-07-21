package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends User {
    @Id
    @Column(name = "uuid")
    @GeneratedValue
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "role", nullable = false)
    private RoleInCompany role;

    @Column(name = "id_of_role_issuer")
    private UUID idOfRoleIssuer;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
