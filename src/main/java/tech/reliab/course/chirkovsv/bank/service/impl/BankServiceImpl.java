package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import main.java.tech.reliab.course.chirkovsv.bank.service.BankService;

import main.java.tech.reliab.course.chirkovsv.bank.service.UserService;
import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.NoSuchElementException;

public class BankServiceImpl implements BankService {
  public static final BigDecimal RATING_MAX_VALUE = new BigDecimal("100");
  public static final BigDecimal TOTAL_MONEY_AMOUNT_MAX_VALUE = new BigDecimal("1000000");
  public static final BigDecimal INTEREST_RATE_MIN_VALUE = new BigDecimal("1");
  public static final BigDecimal INTEREST_RATE_MAX_VALUE = new BigDecimal("20");

  public static final BigDecimal INTEREST_RATE_VALUES_SEGMENT_LENGTH =
      INTEREST_RATE_MAX_VALUE.subtract(INTEREST_RATE_MIN_VALUE);

  private static int nBanks = 0;
  private final List<Bank> banks = new ArrayList<>();

  /**
   * Создаёт новый банк.
   *
   * @param name название банка.
   * @return созданный банк.
   */
  @Override
  public Bank createBank(final String name) {
    Bank bankToCreate = new Bank(name);

    bankToCreate.setId(nBanks++);

    bankToCreate.setRating(getGeneratedRating());
    bankToCreate.setTotalMoneyAmount(getGeneratedTotalMoneyAmount());
    bankToCreate.setInterestRate(getCalculatedInterestRate(bankToCreate.getRating()));

    banks.add(bankToCreate);

    return bankToCreate;
  }

  /**
   * Возвращает сгенерированное значение рейтинга банка.
   *
   * @return сгенерированное значение рейтинга банка
   */
  private BigDecimal getGeneratedRating() {
    return Utils.getBigDecimalGeneratedOnTheSegment(
        RATING_MAX_VALUE
    );
  }

  /**
   * Возвращает сгенерированное значение общего количества
   * денег в банке.
   *
   * @return сгенерированное значение общего количества
   * денег в банке.
   */
  private BigDecimal getGeneratedTotalMoneyAmount() {
    return Utils.getBigDecimalGeneratedOnTheSegment(
        TOTAL_MONEY_AMOUNT_MAX_VALUE
    );
  }

  /**
   * Возвращает вычисленное значение процентной ставки.
   *
   * @param rating рейтинг банка.
   * @return вычисленное значение процентной ставки.
   */
  private BigDecimal getCalculatedInterestRate(final BigDecimal rating) {
    double ratingFactor = Math.sqrt(rating.doubleValue() / 100.0);

    BigDecimal adjustedInterestRateMaxValue =
        INTEREST_RATE_MIN_VALUE.add(
            INTEREST_RATE_VALUES_SEGMENT_LENGTH.multiply(
                new BigDecimal(1 - ratingFactor)));

    return Utils.getBigDecimalGeneratedOnTheSegment(
        INTEREST_RATE_MIN_VALUE,
        adjustedInterestRateMaxValue);
  }

  /**
   * Возвращает список со всеми банками.
   *
   * @return список со всеми банками.
   */
  @Override
  public List<Bank> getAllBanks() {
    return new ArrayList<>(banks);
  }

  /**
   * Возвращает банк с указанным идентификатором.
   *
   * @param id идентификатор искомого банка.
   * @return банк с указанным идентификатором.
   */
  @Override
  public Optional<Bank> getBankById(final int id) {
    return banks
        .stream()
        .filter(bank -> bank.getId() == id)
        .findFirst();
  }

  /**
   * возвращает банк с указанным идентификатором, если он существует.
   *
   * @param id идентификатор искомого банка.
   * @return банк с указанным идентификатором, если он существует.
   */
  private Bank getBankIfExists(final int id) {
    return getBankById(id)
        .orElseThrow(() -> new NoSuchElementException("bank was not found"));
  }

  /**
   * Изменяет банк с указанным идентификатором.
   *
   * @param id      идентификатор искомого банка.
   * @param newName новое название банка.
   */
  @Override
  public void updateBank(final int id, final String newName) {
    Bank bankToUpdate = getBankIfExists(id);

    Utils.validateNewName(newName, bankToUpdate.getName());

    bankToUpdate.setName(newName);
  }

  /**
   * Добавляет офис в банк.
   *
   * @param bank   банк, в который добавляется офис;
   * @param office офис, добавляемый в банк.
   */
  @Override
  public void addOffice(final Bank bank, final BankOffice office) {
    Utils.validateParametersOnNonNullValues(bank, office);
    Utils.validateReferringToParentEntity(bank, office, false);

    bank.incNOffices(Utils.INC_DEFAULT_VALUE);
    office.setBank(bank);
  }

  /**
   * Исключает офис из банка.
   *
   * @param bank   банк, из которого исключается офис;
   * @param office офис, исключаемый из банка.
   */
  @Override
  public void removeOffice(final Bank bank, final BankOffice office) {
    Utils.validateParametersOnNonNullValues(bank, office);
    Utils.validateReferringToParentEntity(bank, office, true);

    bank.incNOffices(-Utils.INC_DEFAULT_VALUE);
    office.setBank(null);
  }

  /**
   * Добавляет банкомат в банк.
   *
   * @param bank банк, в который добавляется банкомат;
   * @param atm  банкомат, добавляемый в банк.
   */
  @Override
  public void addAtm(final Bank bank, final BankAtm atm) {
    Utils.validateParametersOnNonNullValues(bank, atm);
    Utils.validateReferringToParentEntity(bank, atm, false);

    bank.incNAtms(Utils.INC_DEFAULT_VALUE);
    atm.setBank(bank);
  }

  /**
   * Исключает банкомат из банка.
   *
   * @param bank банк, из которого исключается банкомат;
   * @param atm  банкомат, исключаемый из банка.
   */
  @Override
  public void removeAtm(final Bank bank, final BankAtm atm) {
    Utils.validateParametersOnNonNullValues(bank, atm);
    Utils.validateReferringToParentEntity(bank, atm, true);

    bank.incNAtms(-Utils.INC_DEFAULT_VALUE);
    atm.setBank(null);
  }

  /**
   * Добавляет работника в банк.
   *
   * @param bank     банк, в который добавляется работник;
   * @param employee работник, добавляемый в банк.
   */
  @Override
  public void addEmployee(final Bank bank, final Employee employee) {
    Utils.validateParametersOnNonNullValues(bank, employee);
    Utils.validateReferringToParentEntity(bank, employee, false);

    bank.incNEmployees(Utils.INC_DEFAULT_VALUE);
    employee.setBank(bank);
  }

  /**
   * Исключает работника из банка.
   *
   * @param bank     банк, из которого исключается работник;
   * @param employee работник, исключаемый из банка.
   */
  @Override
  public void removeEmployee(final Bank bank, final Employee employee) {
    Utils.validateParametersOnNonNullValues(bank, employee);
    Utils.validateReferringToParentEntity(bank, employee, true);

    bank.incNEmployees(-Utils.INC_DEFAULT_VALUE);
    employee.setBank(null);
  }

  /**
   * Добавляет пользователя в банк.
   *
   * @param bank банк, в который добавляется пользователь;
   * @param user пользователь, добавляемый в банк.
   */
  @Override
  public void addUser(final Bank bank, final User user) {
    Utils.validateParametersOnNonNullValues(bank, user);
    Utils.validateReferringToParentEntity(bank, user, false);

    bank.incNUsers(Utils.INC_DEFAULT_VALUE);
  }

  /**
   * Исключает пользователя из банка.
   *
   * @param bank банк, из которого исключается пользователь;
   * @param user пользователь, исключаемый из банка.
   */
  @Override
  public void removeUser(final Bank bank, final User user) {
    Utils.validateParametersOnNonNullValues(bank, user);
    Utils.validateReferringToParentEntity(bank, user, true);

    bank.incNUsers(-Utils.INC_DEFAULT_VALUE);
  }

  /**
   * Вносит деньги в банк.
   *
   * @param bank                 банк, в который вносятся деньги;
   * @param moneyAmountToDeposit сумма денег, вносимая в банк.
   */
  @Override
  public void depositMoney(
      final Bank bank,
      final BigDecimal moneyAmountToDeposit) {
    if (Utils.isBigDecimalMoreThanZero(moneyAmountToDeposit)) {
      bank.incTotalMoneyAmount(moneyAmountToDeposit);
    } else {
      throw new IllegalArgumentException(
          "money amount to deposit must be greater than zero"
      );
    }
  }

  /**
   * Выводит деньги из банка.
   *
   * @param bank                  банк, из которого выводятся деньги;
   * @param moneyAmountToWithdraw сумма денег, выводимая из банка.
   */
  @Override
  public void withdrawMoney(
      final Bank bank,
      final BigDecimal moneyAmountToWithdraw) {
    if (!Utils.isBigDecimalMoreThanZero(moneyAmountToWithdraw)) {
      throw new IllegalArgumentException(
          "money amount to withdraw must be greater than zero"
      );
    }

    if (Utils.isBigDecimalMoreThanUpperBound(
        moneyAmountToWithdraw,
        bank.getTotalMoneyAmount())
    ) {
      throw new IllegalArgumentException(
          "money amount to withdraw must less or equal to total money amount"
      );
    } else {
      bank.incTotalMoneyAmount(moneyAmountToWithdraw.negate());
    }
  }

  /**
   * Удаляет банк с указанным идентификатором.
   *
   * @param id идентификатор искомого банка.
   */
  @Override
  public void deleteBank(final int id) {
    Bank bankToDelete = getBankIfExists(id);

    if (bankToDelete.getNOffices() +
        bankToDelete.getNAtms() +
        bankToDelete.getNEmployees() +
        bankToDelete.getNClients() > 0) {
      throw new IllegalStateException("cannot delete bank with associated entities.");
    }

    banks.remove(bankToDelete);
  }
}