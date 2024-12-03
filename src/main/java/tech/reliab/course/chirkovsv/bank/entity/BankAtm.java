package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;

// сущность "банкомат".
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

  public int getId() {
    return id;
  }

  public void setId(final int newIdValue) {
    id = newIdValue;
  }

  public String getName() {
    return name;
  }

  public void setName(final String newName) {
    name = newName;
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

  public Bank getBank() {
    return bank;
  }

  public void setBank(final Bank newBank) {
    bank = newBank;
  }

  public BankOffice getOffice() {
    return office;
  }

  public void setOffice(final BankOffice newOffice) {
    office = newOffice;
  }

  public String getLocationInTheOffice() {
    return locationInTheOffice;
  }

  public void setLocationInTheOffice(final String newLocationInTheOfficeValue) {
    locationInTheOffice = newLocationInTheOfficeValue;
  }

  public Employee getServingEmployee() {
    return servingEmployee;
  }

  public void setServingEmployee(final Employee newServingEmployee) {
    servingEmployee = newServingEmployee;
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

  public BigDecimal getMoneyAmount() {
    return moneyAmount;
  }

  public void setMoneyAmount(final BigDecimal newMoneyAmountValue) {
    moneyAmount = newMoneyAmountValue;
  }

  public BigDecimal getMaintenanceCost() {
    return maintenanceCost;
  }

  public void setMaintenanceCost(final BigDecimal newMaintenanceCostValue) {
    maintenanceCost = newMaintenanceCostValue;
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