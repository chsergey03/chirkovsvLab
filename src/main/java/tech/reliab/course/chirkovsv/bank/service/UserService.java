package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.CreditAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.PaymentAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
  User createUser(
      final String fullName,
      final LocalDate birthDate,
      final String workplace
  );

  List<User> getAllUsers();

  Optional<User> getUserById(int id);

  void updateUser(final int id, final String newFullName);

  public boolean doesUserHaveBank(final User user, final int bankId);

  void deleteUser(final int id);
}