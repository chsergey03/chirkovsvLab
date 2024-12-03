package main.java.tech.reliab.course.chirkovsv.bank.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
  private Bank bank;
  private User user;

  public Account(Bank bank, User user) {
    this.bank = bank;
    this.user = user;
  }
}
