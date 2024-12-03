package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.BankAtm;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankOffice;
import main.java.tech.reliab.course.chirkovsv.bank.entity.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankAtmService {
  BankAtm createAtm(
      final BankOffice office,
      final String name,
      final String locationInTheOffice,
      final Employee servingEmployee,
      final boolean isDepositAvailable,
      final boolean isWithdrawAvailable,
      final BigDecimal maintenanceCost
  );

  List<BankAtm> getAllAtms();

  Optional<BankAtm> getAtmById(final int id);

  void updateAtm(final int id, final String newName);

  void switchIsDepositAvailable(final BankAtm atm);

  void switchIsWithdrawAvailable(final BankAtm atm);

  void depositMoney(final BankAtm atm, final BigDecimal moneyAmountToDeposit);

  void withdrawMoney(final BankAtm atm, final BigDecimal moneyAmountToWithdraw);

  boolean doesOfficeHaveAnyAssociatedAtm(final int id);

  void deleteAtm(final int id);
}