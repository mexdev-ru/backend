package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "quotaLicense")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuotaLicense {
  @Id
  @Column(name = "id")
  @GeneratedValue
  private UUID uuid;

  @Column(name = "amount")
  private int amount;

  public User getUser() {
    return user;
  }

  @Column(name = "period")
  //@JsonFormat(pattern="yyyy-MM-dd");
  private int period;

  @OneToOne
  @JoinColumn
  private User user;

  public QuotaLicense(int amount, int period, User user) {
    this.amount = amount;
    this.period = period;
    this.user = user;
  }

  public QuotaLicense(int amount) {
    this.amount = amount;
  }

  public QuotaLicense() {

  }

  public int getAmount() {
    return amount;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public UUID getUuid() {
    return uuid;
  }

  public int getPeriod() {
    return period;
  }
}
