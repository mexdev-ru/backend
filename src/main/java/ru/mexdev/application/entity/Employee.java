package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
  @Id
  @Column(name = "uuid")
  @GeneratedValue
  private UUID uuid;

  @OneToMany(mappedBy = "employee", orphanRemoval = true)
  private List<RoleInCompany> roles;

  @OneToOne
  @JoinColumn
  private User userId;

  public void setUserId(User userId) {
    this.userId = userId;
  }

  public User getUserId() {
    return userId;
  }

  public Employee() {
    this.roles = new ArrayList<>();
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public List<RoleInCompany> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleInCompany> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "uuid=" + uuid +
        ", roles=" + roles +
        '}';
  }
}
