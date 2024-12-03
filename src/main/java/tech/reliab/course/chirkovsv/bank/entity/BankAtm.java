package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

// сущность "банкомат".
@Getter
@Setter
public class BankAtm {
  public enum Status {
    WORKING,
    NOT_WORKING,
    NO_MONEY
  }

  private int id;
  private String name;
  private String address;
  private Status status;
  private Bank bank;
  private BankOffice office;
  private String locationInTheOffice;
  private Employee servingEmployee;
  private boolean isDepositAvailable;
  private boolean isWithdrawAvailable;
  private BigDecimal moneyAmount;
  private BigDecimal maintenanceCost;

  public BankAtm(
      final String name,
      final String locationInTheOffice,
      final Employee servingEmployee,
      final boolean isDepositAvailable,
      final boolean isWithdrawAvailable,
      final BigDecimal maintenanceCost
  ) {
    this.name = name;
    this.locationInTheOffice = locationInTheOffice;
    this.servingEmployee = servingEmployee;
    this.isDepositAvailable = isDepositAvailable;
    this.isWithdrawAvailable = isWithdrawAvailable;
    this.maintenanceCost = maintenanceCost;
  }

  public void incMoneyAmount(final BigDecimal incValue) {
    moneyAmount = moneyAmount.add(incValue);
  }

  @Override
  public String toString() {
    return "BankAtm {\n  id = " + id +
      "\n, name = " + name +
      "\n, address = " + address +
      "\n, status = " + status +
      "\n, bank = " + bank.getName() +
      "\n, locationInTheOffice = " + locationInTheOffice +
      "\n, servingEmployee = " + servingEmployee.getFullName() +
      "\n, isDepositAvailable = " + isDepositAvailable +
      "\n, isWithdrawAvailable = " + isWithdrawAvailable +
      "\n, moneyAmount = " + String.format("%.2f", moneyAmount) +
      "\n, maintenanceCost = " + String.format("%.2f", maintenanceCost) +
      "\n};\n";
  }
}