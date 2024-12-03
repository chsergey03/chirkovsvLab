package main.java.tech.reliab.course.chirkovsv.bank.entity;

public class Account {
  private Bank bank;
  private User user;

  public Account(Bank bank, User user) {
    this.bank = bank;
    this.user = user;
  }

  public Bank getBank() {
    return bank;
  }

  public void setBank(Bank bank) {
    this.bank = bank;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
