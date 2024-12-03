package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


// сущность "кредитный счёт".
@Getter
@Setter
public class CreditAccount extends Account {
  private int id;
  private LocalDate startDate;
  private LocalDate endDate;
  private int nMonths;
  private BigDecimal moneyAmount;
  private BigDecimal monthlyPayment;
  private BigDecimal interestRate;
  private Employee employeeProvidedCredit;
  private PaymentAccount paymentAccount;

  public CreditAccount(
    final Bank bank,
    final User user,
    final LocalDate startDate,
    final int nMonths,
    final Employee employeeProvidedCredit,
    final PaymentAccount paymentAccount
  ) {
    super(bank, user);

    this.startDate = startDate;
    this.nMonths = nMonths;
    this.employeeProvidedCredit = employeeProvidedCredit;
    this.paymentAccount = paymentAccount;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    CreditAccount creditAccount = (CreditAccount) obj;

    return id == creditAccount.id &&
        super.getBank().equals(creditAccount.getBank());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, super.getBank());
  }

  @Override
  public java.lang.String toString() {
    return "CreditAccount {\n  id = " + id +
      "\n, userFullName = " + super.getUser().getFullName() +
      "\n, bankName = " + super.getBank().getName() +
      "\n, startDate = " + startDate +
      "\n, endDate = " + endDate +
      "\n, nMonths = " + nMonths +
      "\n, moneyAmount = " + String.format("%.2f", moneyAmount) +
      "\n, monthlyPayment = " + String.format("%.2f", monthlyPayment) +
      "\n, interestRate = " + String.format("%.2f", interestRate) +
      "\n, employeeProvidedCredit = " + employeeProvidedCredit.getFullName() +
      "\n, paymentAccountID = " + paymentAccount.getId() +
      "\n};\n";
  }
}