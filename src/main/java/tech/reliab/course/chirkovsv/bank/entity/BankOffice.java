package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

// сущность "офис банка".
@Getter
@Setter
public class BankOffice {
  public enum Status {
    WORKING,
    NOT_WORKING
  }

  private int id;
  private String name;
  private Bank bank;
  private String address;
  private Status status;
  private boolean isAtmPlacingAvailable;
  private boolean isCreditRegistrationAvailable;
  private boolean isDepositAvailable;
  private boolean isWithdrawAvailable;
  private BigDecimal totalMoneyAmount;
  private BigDecimal rentalCost;

  public BankOffice(
    final String name,
    final String address,
    final boolean isAtmPlacingAvailable,
    final boolean isCreditRegistrationAvailable,
    final boolean isDepositAvailable,
    final boolean isWithdrawAvailable,
    final BigDecimal rentalCost
  ) {
    this.name = name;
    this.address = address;
    this.isAtmPlacingAvailable = isAtmPlacingAvailable;
    this.isCreditRegistrationAvailable = isCreditRegistrationAvailable;
    this.isDepositAvailable = isDepositAvailable;
    this.isWithdrawAvailable = isWithdrawAvailable;
    this.rentalCost = rentalCost;
  }

  public void incTotalMoneyAmount(final BigDecimal incAmount) {
    totalMoneyAmount = totalMoneyAmount.add(incAmount);
  }

  @Override
  public String toString() {
    return "BankOffice {\n  id = " + id +
      "\n, name = " + name +
      "\n, bank = " + bank.getName() +
      "\n, address = " + address +
      "\n, status = " + status +
      "\n, isAtmPlacingAvailable = " + isAtmPlacingAvailable +
      "\n, isCreditRegistrationAvailable = " + isCreditRegistrationAvailable +
      "\n, isDepositAvailable = " + isDepositAvailable +
      "\n, isWithdrawAvailable = " + isWithdrawAvailable +
      "\n, moneyAmount = " + String.format("%.2f", totalMoneyAmount) +
      "\n, rentalCost = " + String.format("%.2f", rentalCost) +
      "\n};\n";
  }
}