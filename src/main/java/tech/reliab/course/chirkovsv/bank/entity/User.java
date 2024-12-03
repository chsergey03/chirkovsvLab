package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// сущность "пользователь".
@Getter
@Setter
public class User {
  private int id;
  private String fullName;
  private LocalDate birthDate;
  private String workplace;
  private BigDecimal monthlyIncome;
  private List<Bank> banks;
  private List<CreditAccount> creditAccounts;
  private List<PaymentAccount> paymentAccounts;
  private BigDecimal creditRating;

  public User(
    final String fullName,
    final LocalDate birthDate,
    final String workplace
  ) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.workplace = workplace;

    this.banks = new ArrayList<Bank>();
    this.creditAccounts = new ArrayList<CreditAccount>();
    this.paymentAccounts = new ArrayList<PaymentAccount>();
  }

  public void addBank(final Bank bankToAdd) {
    banks.add(bankToAdd);
  }

  public void removeBank(final Bank bankToRemove) {
    banks.remove(bankToRemove);
  }

  public void addCreditAccount(final CreditAccount creditAccountToAdd) {
    creditAccounts.add(creditAccountToAdd);
  }

  public void removeCreditAccount(final CreditAccount creditAccountToRemove) {
    creditAccounts.remove(creditAccountToRemove);
  }

  public void addPaymentAccount(final PaymentAccount paymentAccountToAdd) {
    paymentAccounts.add(paymentAccountToAdd);
  }

  public void removePaymentAccount(final PaymentAccount paymentAccountToRemove) {
    paymentAccounts.remove(paymentAccountToRemove);
  }

  @Override
  public String toString() {
    return "User {\n  id = " + id +
      "\n fullName = " + fullName +
      "\n birthDate = " + birthDate +
      "\n workplace = " + workplace +
      "\n monthlyIncome = " + String.format("%.2f", monthlyIncome) +
      "\n banks = " + banks +
      "\n creditAccounts = " + creditAccounts +
      "\n paymentAccounts = " + paymentAccounts +
      "\n creditRating = " + String.format("%.2f", creditRating) +
      "\n};\n";
  }
}