package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankAtm;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankOffice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankOfficeService {
  BankOffice createOffice(
    final Bank bank,
    final String name,
    final String address,
    final boolean isAtmPlacingAvailable,
    final boolean isCreditRegistrationAvailable,
    final boolean isDepositAvailable,
    final boolean isWithdrawAvailable,
    final BigDecimal rentalCost
  );

  List<BankOffice> getAllOffices();

  Optional<BankOffice> getOfficeById(final int id);

  void updateOffice(final int id, final String newName);

  void switchIsAtmPlacingAvailable(final BankOffice office);

  void switchIsCreditRegistrationAvailable(final BankOffice office);

  void switchIsDepositAvailable(final BankOffice office);

  void switchIsWithdrawAvailable(final BankOffice office);

  void addAtm(final BankOffice office, final BankAtm atm);

  void removeAtm(final BankOffice office, final BankAtm atm);

  void depositMoney(final BankOffice office, final BigDecimal moneyAmountToDeposit);

  void withdrawMoney(final BankOffice office, final BigDecimal moneyAmountToWithdraw);

  void deleteOffice(final int id);
}