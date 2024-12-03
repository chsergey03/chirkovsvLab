package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;
import java.util.Objects;

// сущность "банк".
public class Bank {
  private int id;
  private String name;
  private int nOffices = 0;
  private int nAtms = 0;
  private int nEmployees = 0;
  private int nUsers = 0;
  private BigDecimal rating;
  private BigDecimal totalMoneyAmount;
  private BigDecimal interestRate;

  public Bank(
    final String name
  ) {
    this.name = name;
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

  public int getNOffices() {
    return nOffices;
  }

  public void setNOffices(final int newNOfficesValue) {
    nOffices = newNOfficesValue;
  }

  public int getNAtms() {
    return nAtms;
  }

  public void setNAtms(final int newNAtmsValue) {
    nAtms = newNAtmsValue;
  }

  public int getNEmployees() {
    return nEmployees;
  }

  public void setNEmployees(final int newNEmployeesValue) {
    nEmployees = newNEmployeesValue;
  }

  public int getNUsers() {
    return nUsers;
  }

  public void setNUsers(final int newNUsersValue) {
    nUsers = newNUsersValue;
  }

  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(final BigDecimal newRatingValue) {
    rating = newRatingValue;
  }

  public BigDecimal getTotalMoneyAmount() {
    return totalMoneyAmount;
  }

  public void setTotalMoneyAmount(final BigDecimal newTotalMoneyAmountValue) {
    totalMoneyAmount = newTotalMoneyAmountValue;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(final BigDecimal newInterestRateValue) {
    interestRate = newInterestRateValue;
  }

  public void incNOffices(final int incValue) {
    nOffices += incValue;
  }

  public void incNAtms(final int incValue) {
    nAtms += incValue;
  }

  public void incNEmployees(final int incValue) {
    nEmployees += incValue;
  }

  public void incNUsers(final int incValue) {
    nUsers += incValue;
  }

  public void incTotalMoneyAmount(final BigDecimal incValue) { totalMoneyAmount = totalMoneyAmount.add(incValue); }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Bank bank = (Bank) obj;

    return id == bank.id &&
        name.equals(bank.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Bank {\n  id = " + id +
      "\n, name = " + name +
      "\n, nOffices = " + nOffices +
      "\n, nAtms = " + nAtms +
      "\n, nEmployees = " + nEmployees +
      "\n, nUsers = " + nUsers +
      "\n, rating = " + String.format("%.2f", rating) +
      "\n, totalMoneyAmount = " + String.format("%.2f", totalMoneyAmount) +
      "\n, interestRate = " + String.format("%.2f", interestRate) +
      "\n};\n";
  }
}