package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;

// сущность "офис банка".
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

  public int getId() {
    return id;
  }

  public void setId(final int newIdValue) {
    id = newIdValue;
  }

  public String getName() {
    return name;
  }

  public void setName(final String newNameValue) {
    name = newNameValue;
  }

  public Bank getBank() {
    return bank;
  }

  public void setBank(final Bank newBank) {
    bank = newBank;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String newAddressValue) {
    address = newAddressValue;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(final Status newStatusValue) {
    status = newStatusValue;
  }

  public boolean getIsAtmPlacingAvailable() {
    return isAtmPlacingAvailable;
  }

  public void setIsAtmPlacingAvailable(final boolean newIsAtmPlacingAvailableValue) {
    isAtmPlacingAvailable = newIsAtmPlacingAvailableValue;
  }

  public boolean getIsCreditRegistrationAvailable() {
    return isCreditRegistrationAvailable;
  }

  public void setIsCreditRegistrationAvailable(final boolean newIsCreditRegistrationAvailableValue) {
    isCreditRegistrationAvailable = newIsCreditRegistrationAvailableValue;
  }

  public boolean getIsDepositAvailable() {
    return isDepositAvailable;
  }

  public void setIsDepositAvailable(final boolean newIsDepositAvailableValue) {
    isDepositAvailable = newIsDepositAvailableValue;
  }

  public boolean getIsWithdrawAvailable() {
    return isWithdrawAvailable;
  }

  public void setIsWithdrawAvailable(final boolean newIsWithdrawAvailableValue) {
    isWithdrawAvailable = newIsWithdrawAvailableValue;
  }

  public BigDecimal getTotalMoneyAmount() {
    return totalMoneyAmount;
  }

  public void setTotalMoneyAmount(final BigDecimal newTotalMoneyAmountValue) {
    totalMoneyAmount = newTotalMoneyAmountValue;
  }

  public BigDecimal getRentalCost() {
    return rentalCost;
  }

  public void setRentalCost(final BigDecimal newRentalCostValue) {
    rentalCost = newRentalCostValue;
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