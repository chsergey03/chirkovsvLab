package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import main.java.tech.reliab.course.chirkovsv.bank.service.BankAtmService;
import main.java.tech.reliab.course.chirkovsv.bank.service.BankService;
import main.java.tech.reliab.course.chirkovsv.bank.service.BankOfficeService;

import main.java.tech.reliab.course.chirkovsv.bank.service.EmployeeService;
import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.NoSuchElementException;

public class BankOfficeServiceImpl implements BankOfficeService {
  private static int nOffices = 0;
  private final List<BankOffice> offices = new ArrayList<>();

  private final BankService bankService;
  private final BankAtmService bankAtmService;
  private final EmployeeService employeeService;

  public BankOfficeServiceImpl(
      BankService bankService,
      BankAtmService bankAtmService,
      EmployeeService employeeService
  ) {
    this.bankService = bankService;
    this.bankAtmService = bankAtmService;
    this.employeeService = employeeService;
  }

  /**
   * Создаёт новый банковский офис.
   *
   * @param bank                          банк, к которому относится офис;
   * @param name                          название офиса;
   * @param address                       адрес;
   * @param isAtmPlacingAvailable         флаг доступности размещения банкомат;
   * @param isCreditRegistrationAvailable флаг доступности выдачи кредитов;
   * @param isDepositAvailable            флаг доступности внесения денег;
   * @param isWithdrawAvailable           флаг доступности вывода денег;
   * @param rentalCost                    стоимость аренды.
   * @return созданный банковский офис.
   */
  @Override
  public BankOffice createOffice(
      final Bank bank,
      final String name,
      final String address,
      final boolean isAtmPlacingAvailable,
      final boolean isCreditRegistrationAvailable,
      final boolean isDepositAvailable,
      final boolean isWithdrawAvailable,
      final BigDecimal rentalCost
  ) {
    BankOffice officeToCreate = new BankOffice(
        name,
        address,
        isAtmPlacingAvailable,
        isCreditRegistrationAvailable,
        isDepositAvailable,
        isWithdrawAvailable,
        rentalCost
    );

    officeToCreate.setId(nOffices++);
    officeToCreate.setStatus(Utils.getRandomEnumValue(BankOffice.Status.class));
    officeToCreate.setTotalMoneyAmount(getGeneratedTotalMoneyAmount(bank));

    bankService.addOffice(bank, officeToCreate);

    offices.add(officeToCreate);

    return officeToCreate;
  }

  /**
   * возвращает сгенерированное значение количества
   * денег в банковском офисе.
   *
   * @param bank банк, к которому относится офис.
   * @return сгенерированное значение количества денег
   * в банковском офисе.
   */
  private BigDecimal getGeneratedTotalMoneyAmount(final Bank bank) {
    return Utils.getBigDecimalGeneratedOnTheSegment(bank.getTotalMoneyAmount());
  }

  /**
   * Возвращает список со всеми банковскими офисами.
   *
   * @return список со всеми банковскими офисами.
   */
  @Override
  public List<BankOffice> getAllOffices() {
    return new ArrayList<>(offices);
  }

  /**
   * Возвращает банковский офис с указанным идентификатором.
   *
   * @param id идентификатор искомого банковского офиса.
   * @return банковский офис с указанным идентификатором.
   */
  @Override
  public Optional<BankOffice> getOfficeById(final int id) {
    return offices
        .stream()
        .filter(office -> office.getId() == id)
        .findFirst();
  }

  /**
   * возвращает банковский офис с указанным идентификатором,
   * если он существует.
   *
   * @param id идентификатор искомого банковского офиса.
   * @return банковский офис с указанным идентификатором,
   * если он существует.
   */
  private BankOffice getOfficeIfExists(final int id) {
    return getOfficeById(id)
        .orElseThrow(() -> new NoSuchElementException("office was not found"));
  }

  /**
   * Изменяет банковский офис с указанным идентификатором.
   *
   * @param id      идентификатор искомого банковского офиса.
   * @param newName новое название банковского офиса.
   */
  @Override
  public void updateOffice(final int id, final String newName) {
    BankOffice officeToUpdate = getOfficeIfExists(id);

    Utils.validateNewName(newName, officeToUpdate.getName());

    officeToUpdate.setName(newName);
  }

  /**
   * Меняет значение флага доступности размещения банкоматов
   * в банковском офисе на противоположный.
   *
   * @param office банковский офис, у которого меняется
   *               значение флага доступности размещения
   *               банкоматов на противоположный.
   */
  @Override
  public void switchIsAtmPlacingAvailable(final BankOffice office) {
    office.setIsAtmPlacingAvailable(!office.getIsAtmPlacingAvailable());
  }

  /**
   * Меняет значение флага доступности выдачи кредитов
   * в банковском офисе на противоположный.
   *
   * @param office банковский офис, у которого меняется
   *               значение флага доступности выдачи
   *               кредитов на противоположный.
   */
  @Override
  public void switchIsCreditRegistrationAvailable(final BankOffice office) {
    office.setIsCreditRegistrationAvailable(!office.getIsCreditRegistrationAvailable());
  }

  /**
   * Меняет значение флага доступности внесения денег
   * в банковский офис на противоположный.
   *
   * @param office банковский офис, у которого меняется
   *               значение флага доступности внесения
   *               денег на противоположный.
   */
  @Override
  public void switchIsDepositAvailable(final BankOffice office) {
    office.setIsDepositAvailable(!office.getIsDepositAvailable());
  }

  /**
   * Меняет значение флага доступности вывода денег
   * из банковского офиса на противоположный.
   *
   * @param office банковский офис, у которого меняется
   *               значение флага доступности вывода
   *               денег на противоположный.
   */
  @Override
  public void switchIsWithdrawAvailable(final BankOffice office) {
    office.setIsWithdrawAvailable(!office.getIsWithdrawAvailable());
  }

  /**
   * Добавляет банкомат в банковский офис.
   *
   * @param office офис, в который добавляется банкомат;
   * @param atm    банкомат, добавляемый в офис.
   */
  @Override
  public void addAtm(final BankOffice office, final BankAtm atm) {
    if (office.getIsAtmPlacingAvailable()) {
      Utils.validateParametersOnNonNullValues(office, atm);
      Utils.validateReferringToParentEntity(office, atm, false);

      atm.setOffice(office);
    } else {
      throw new IllegalArgumentException("atm placing is not available");
    }

  }

  /**
   * Исключает банкомат из банковского офиса.
   *
   * @param office офис, из которого исключается банкомат;
   * @param atm    банкомат, исключаемый из офиса.
   */
  @Override
  public void removeAtm(final BankOffice office, final BankAtm atm) {
    Utils.validateParametersOnNonNullValues(office, atm);
    Utils.validateReferringToParentEntity(office, atm, true);

    atm.setOffice(null);
  }

  /**
   * Вносит деньги в банковский офис.
   *
   * @param office               офис, в который вносятся деньги;
   * @param moneyAmountToDeposit сумма денег, вносимая в банковский офис.
   */
  @Override
  public void depositMoney(
      final BankOffice office,
      final BigDecimal moneyAmountToDeposit
  ) {
    if (office.getIsDepositAvailable()) {
      if (Utils.isBigDecimalMoreThanZero(moneyAmountToDeposit)) {
        office.incTotalMoneyAmount(moneyAmountToDeposit);

        bankService.depositMoney(office.getBank(), moneyAmountToDeposit);
      } else {
        throw new IllegalArgumentException(
            "money amount to deposit must be greater than zero"
        );
      }
    } else {
      throw new IllegalStateException("deposit is not available");
    }

  }

  /**
   * Выводит деньги из банковского офиса.
   *
   * @param office                офис, из которого выводятся деньги;
   * @param moneyAmountToWithdraw сумма денег, выводимая из банковского офиса.
   */
  @Override
  public void withdrawMoney(
      final BankOffice office,
      final BigDecimal moneyAmountToWithdraw
  ) {
    if (office.getIsWithdrawAvailable()) {
      if (!Utils.isBigDecimalMoreThanZero(moneyAmountToWithdraw)) {
        throw new IllegalArgumentException(
            "money amount to withdraw must be greater than zero"
        );
      }

      if (Utils.isBigDecimalMoreThanUpperBound(
          moneyAmountToWithdraw,
          office.getTotalMoneyAmount())
      ) {
        throw new IllegalArgumentException(
            "money amount to withdraw must less or equal to zero"
        );
      } else {
        office.incTotalMoneyAmount(moneyAmountToWithdraw.negate());
      }
    } else {
      throw new IllegalStateException("withdraw is not available");
    }
  }

  /**
   * Возвращает значение 'истина', если банковский офис
   * имеет хотя бы одного связанного с ним работника банка,
   * в противном случае - 'ложь'.
   *
   * @param id идентификатор искомого банковского офиса.
   * @return 'истина' или 'ложь'.
   */
  private boolean doesOfficeHaveAnyAssociatedEmployee(
      final int id
  ) {
    List<Employee> employees = employeeService.getAllEmployees();

    for (Employee employee : employees) {
      if (employee.getOffice() != null &&
          employee.getOffice().getId() == id) {
        return true;
      }
    }

    return false;
  }

  /**
   * Удаляет банковский офис с указанным идентификатором.
   *
   * @param id идентификатор искомого банковского офиса.
   */
  @Override
  public void deleteOffice(final int id) {
    BankOffice officeToDelete = getOfficeIfExists(id);

    if (bankAtmService.doesOfficeHaveAnyAssociatedAtm(id)) {
      throw new IllegalStateException(
          "office has at least one atm"
      );
    }

    if (doesOfficeHaveAnyAssociatedEmployee(id)) {
      throw new IllegalStateException(
          "office has at least one employee"
      );
    }

    bankService.removeOffice(officeToDelete.getBank(), officeToDelete);

    offices.remove(officeToDelete);
  }
}
