package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;

public interface BankService {
  Bank createBank(final String name);

  List<Bank> getAllBanks();

  Optional<Bank> getBankById(final int id);

  void updateBank(final int id, final String newName);

  void addOffice(final Bank bank, final BankOffice office);

  void removeOffice(final Bank bank, final BankOffice office);

  void addEmployee(final Bank bank, final Employee employee);

  void removeEmployee(final Bank bank, final Employee employee);

  void addAtm(final Bank bank, final BankAtm atm);

  void removeAtm(final Bank bank, final BankAtm atm);

  void addUser(final Bank bank, final User user);

  void removeUser(final Bank bank, final User user);

  void depositMoney(final Bank bank, final BigDecimal moneyAmountToDeposit);

  void withdrawMoney(final Bank bank, final BigDecimal moneyAmountToWithdraw);

  void deleteBank(final int id);
}