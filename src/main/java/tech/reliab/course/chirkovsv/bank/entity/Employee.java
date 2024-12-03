package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

// сущность "сотрудник банка".
public class Employee {
  private int id;
  private String fullName;
  private LocalDate birthDate;
  private String post;
  private Bank bank;
  private BankOffice office;
  private boolean doesWorkInTheOffice;
  private boolean hasAbilityToRegisterCredits;
  private BigDecimal salary;

  public Employee(
    final String fullName,
    final LocalDate birthDate,
    final String post,
    final BankOffice office,
    final boolean doesWorkInTheOffice,
    final boolean hasAbilityToRegisterCredits,
    final BigDecimal salary
  ) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.post = post;
    this.office = office;
    this.doesWorkInTheOffice = doesWorkInTheOffice;
    this.hasAbilityToRegisterCredits = hasAbilityToRegisterCredits;
    this.salary = salary;
  }

  public int getId() {
    return id;
  }

  public void setId(final int newIdValue) {
    id = newIdValue;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(final String newFullNameValue) {
    fullName = newFullNameValue;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(final LocalDate newBirthDateValue) {
    birthDate = newBirthDateValue;
  }

  public String getPost() {
    return post;
  }

  public void setPost(final String newPostValue) {
    post = newPostValue;
  }

  public Bank getBank() {
    return bank;
  }

  public void setBank(final Bank newBankValue) {
    bank = newBankValue;
  }

  public boolean getDoesWorkInTheOffice() {
    return doesWorkInTheOffice;
  }

  public void setDoesWorkInTheOffice(final boolean newDoesWorkInTheOfficeValue) {
    doesWorkInTheOffice = newDoesWorkInTheOfficeValue;
  }

  public BankOffice getOffice() {
    return office;
  }

  public void setOffice(final BankOffice newOfficeValue) {
    office = newOfficeValue;
  }

  public boolean getHasAbilityToRegisterCredits() {
    return hasAbilityToRegisterCredits;
  }

  public void setHasAbilityToRegisterCredits(final boolean newHasAbilityToRegisterCreditsValue) {
    hasAbilityToRegisterCredits = newHasAbilityToRegisterCreditsValue;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(final BigDecimal newSalaryValue) {
    salary = newSalaryValue;
  }

  @Override
  public String toString() {
    return "Employee {\n  id = " + id +
      "\n, fullName = " + fullName +
      "\n, birthDate = " + birthDate +
      "\n, post = " + post +
      "\n, bank = " + bank.getName() +
      "\n, doesWorkInTheOffice = " + doesWorkInTheOffice +
      "\n, bankOfficeName = " + (office == null ? "null" : office.getName() ) +
      "\n, hasAbilityToRegisterCredits = " + hasAbilityToRegisterCredits +
      "\n, salary = " + String.format("%.2f", salary) +
      "\n};\n";
  }
}