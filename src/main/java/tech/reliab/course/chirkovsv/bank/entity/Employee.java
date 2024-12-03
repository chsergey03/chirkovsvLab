package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


// сущность "сотрудник банка".
@Getter
@Setter
public class Employee {
  private int id;
  private String fullName;
  private LocalDate birthDate;
  private String post;
  private Bank bank;
  private BankOffice office;
  private boolean isWorkInTheOffice;
  private boolean isAbilityToRegisterCredits;
  private BigDecimal salary;

  public Employee(
    final String fullName,
    final LocalDate birthDate,
    final String post,
    final BankOffice office,
    final boolean isWorkInTheOffice,
    final boolean isAbilityToRegisterCredits,
    final BigDecimal salary
  ) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.post = post;
    this.office = office;
    this.isWorkInTheOffice = isWorkInTheOffice;
    this.isAbilityToRegisterCredits = isAbilityToRegisterCredits;
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "Employee {\n  id = " + id +
      "\n, fullName = " + fullName +
      "\n, birthDate = " + birthDate +
      "\n, post = " + post +
      "\n, bank = " + bank.getName() +
      "\n, doesWorkInTheOffice = " + isWorkInTheOffice +
      "\n, bankOfficeName = " + (office == null ? "null" : office.getName() ) +
      "\n, hasAbilityToRegisterCredits = " + isAbilityToRegisterCredits +
      "\n, salary = " + String.format("%.2f", salary) +
      "\n};\n";
  }
}