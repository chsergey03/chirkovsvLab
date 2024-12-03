package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;

// сущность "кредитный счёт".
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

  public int getId() {
    return id;
  }

  public void setId(final int newIdValue) {
    id = newIdValue;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(final LocalDate newStartDateValue) {
    startDate = newStartDateValue;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(final LocalDate newEndDateValue) {
    endDate = newEndDateValue;
  }

  public int getNMonths() {
    return nMonths;
  }

  public void setNMonths(final int newNMonthsValue) {
    nMonths = newNMonthsValue;
  }

  public BigDecimal getMoneyAmount() {
    return moneyAmount;
  }

  public void setMoneyAmount(final BigDecimal newMoneyAmountValue) {
    moneyAmount = newMoneyAmountValue;
  }

  public BigDecimal getMonthlyPayment() {
    return monthlyPayment;
  }

  public void setMonthlyPayment(final BigDecimal newMonthlyPaymentValue) {
    monthlyPayment = newMonthlyPaymentValue;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(final BigDecimal newInterestRateValue) {
    interestRate = newInterestRateValue;
  }

  public Employee getEmployeeProvidedCredit() {
    return employeeProvidedCredit;
  }

  public void setEmployeeProvidedCredit(final Employee newEmployeeProvidedCredit) {
    employeeProvidedCredit = newEmployeeProvidedCredit;
  }

  public PaymentAccount getPaymentAccount() {
    return paymentAccount;
  }

  public void setPaymentAccount(final PaymentAccount newPaymentAccount) {
    paymentAccount = newPaymentAccount;
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