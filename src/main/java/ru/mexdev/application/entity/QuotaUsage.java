package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "quotaUsage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuotaUsage {
  @Id
  @Column(name = "id")
  @GeneratedValue
  private UUID id;

  @Column(name = "period")
  private int period;

  @Column(name = "counter")
  private int counter;

  @OneToOne
  @JoinColumn
  private User user;

  public QuotaUsage(int period, int counter, User user) {
    this.period = period;
    this.counter = counter;
    this.user = user;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getPeriod() {
    return period;
  }

  public int getCounter() {
    return counter;
  }

  public int incrementCounter() {
    return counter+=1;
  }

  public User getUser() {
    return user;
  }

  public UUID getId() {
    return id;
  }

  public QuotaUsage() {

  }

}
