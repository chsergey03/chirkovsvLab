package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.PaymentAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentAccountService {
  PaymentAccount createPaymentAccount(
      final Bank bank,
      final User user,
      final BigDecimal moneyAmount
  );

  List<PaymentAccount> getAllPaymentAccounts();

  Optional<PaymentAccount> getPaymentAccountById(final int id);

  void updatePaymentAccount(
      final int id,
      final Bank newBank,
      final User newUser
  );

  void updatePaymentAccount(
      final int id,
      final Bank newBank
  );

  void updatePaymentAccount(
      final int id,
      final User newUser
  );

  void deletePaymentAccount(final int id);
}