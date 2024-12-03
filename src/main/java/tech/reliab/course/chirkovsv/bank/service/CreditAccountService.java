package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditAccountService {
  CreditAccount createCreditAccount(
      final Bank bank,
      final User user,
      final LocalDate startDate,
      final int nMonths,
      final BigDecimal moneyAmount,
      final BigDecimal interestRate,
      final Employee employeeProvidedCredit,
      final PaymentAccount paymentAccount
  );

  List<CreditAccount> getAllCreditAccounts();

  Optional<CreditAccount> getCreditAccountById(final int id);

  void updateCreditAccount(
      final int id,
      final Bank bank,
      final User user
  );

  void updateCreditAccount(
      final int id,
      final Bank bank
  );

  void updateCreditAccount(
      final int id,
      final User user
  );

  void deleteCreditAccount(final int id);
}