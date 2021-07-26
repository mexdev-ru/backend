package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
    @Id
    @Column(name = "uuid")
    @GeneratedValue
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "role")
    private RoleInCompany role;

    @Column(name = "id_of_role_issuer")
    private UUID idOfRoleIssuer;

    @Autowired
    public Employee() {
    }

    @Autowired
    public Employee(RoleInCompany role, UUID idOfRoleIssuer) {
        this.role = role;
        this.idOfRoleIssuer = idOfRoleIssuer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public RoleInCompany getRole() {
        return role;
    }

    public void setRole(RoleInCompany role) {
        this.role = role;
    }

    public UUID getIdOfRoleIssuer() {
        return idOfRoleIssuer;
    }

    public void setIdOfRoleIssuer(UUID idOfRoleIssuer) {
        this.idOfRoleIssuer = idOfRoleIssuer;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "uuid=" + uuid +
                ", role=" + role +
                ", idOfRoleIssuer=" + idOfRoleIssuer +
                '}';
    }
}
