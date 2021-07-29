package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles_in_companies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoleInCompany {
  @Id
  @Column(name = "uuid")
  @GeneratedValue
  private UUID uuid;

  @OneToOne
  @JoinColumn
  private Company company;

  @OneToOne
  @JoinColumn
  private Role role;

  @OneToOne
  @JoinColumn
  private Employee roleIssuer;

  @ManyToOne
  @JoinColumn
  private Employee employee;

  public RoleInCompany() {
  }

  public RoleInCompany(Company company, Role role, Employee roleIssuer, Employee employee) {
    this.company = company;
    this.role = role;
    this.roleIssuer = roleIssuer;
    this.employee = employee;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Employee getRoleIssuer() {
    return roleIssuer;
  }

  public void setRoleIssuer(Employee roleIssuer) {
    this.roleIssuer = roleIssuer;
  }

  @JsonIgnore
  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String toString() {
    return "RoleInCompany{" +
        "uuid=" + uuid +
        ", company=" + company +
        ", role=" + role +
        ", roleIssuer=" + roleIssuer +
        ", employee=" + employee.getUuid() +
        '}';
  }
}
