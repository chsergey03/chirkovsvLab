package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.BankAtm;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankOffice;
import main.java.tech.reliab.course.chirkovsv.bank.entity.Employee;

import main.java.tech.reliab.course.chirkovsv.bank.service.BankAtmService;
import main.java.tech.reliab.course.chirkovsv.bank.service.BankService;

import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.NoSuchElementException;

public class BankAtmServiceImpl implements BankAtmService {
  private static int nAtms = 0;
  private final List<BankAtm> atms = new ArrayList<>();

  private final BankService bankService;

  public BankAtmServiceImpl(
      BankService bankService
  ) {
    this.bankService = bankService;
  }

  /**
   * создаёт банкомат.
   *
   * @param office              офис, в котором расположен банкомат;
   * @param name                название банкомата;
   * @param locationInTheOffice расположение в офисе.
   * @param servingEmployee     работник, обслуживающий банкомат
   * @param isDepositAvailable  флаг доступности внесения денег;
   * @param isWithdrawAvailable флаг доступности вывода денег;
   * @param maintenanceCost     стоимость обслуживания.
   * @return созданный банкомат.
   */
  @Override
  public BankAtm createAtm(
      final BankOffice office,
      final String name,
      final String locationInTheOffice,
      final Employee servingEmployee,
      final boolean isDepositAvailable,
      final boolean isWithdrawAvailable,
      final BigDecimal maintenanceCost
  ) {
    BankAtm bankAtmToCreate = new BankAtm(
        name,
        locationInTheOffice,
        servingEmployee,
        isDepositAvailable,
        isWithdrawAvailable,
        maintenanceCost
    );

    bankAtmToCreate.setId(nAtms++);
    bankAtmToCreate.setStatus(Utils.getRandomEnumValue(BankAtm.Status.class));
    bankAtmToCreate.setMoneyAmount(getGeneratedMoneyAmount(office));

    bankService.addAtm(office.getBank(), bankAtmToCreate);

    atms.add(bankAtmToCreate);

    return bankAtmToCreate;
  }

  /**
   * возвращает сгенерированное значение количества денег
   * в банкомате.
   *
   * @param office офис, в расположен банкомат.
   * @return сгенерированное значение количества денег в
   * банкомате.
   */
  private BigDecimal getGeneratedMoneyAmount(final BankOffice office) {
    return Utils.getBigDecimalGeneratedOnTheSegment(office.getTotalMoneyAmount());
  }

  /**
   * Возвращает список со всеми банкоматами.
   *
   * @return список со всеми банкоматами.
   */
  @Override
  public List<BankAtm> getAllAtms() {
    return new ArrayList<>(atms);
  }

  /**
   * Возвращает банкомат с указанным идентификатором.
   *
   * @param id идентификатор искомого банкомата.
   * @return банкомат с указанным идентификатором.
   */
  @Override
  public Optional<BankAtm> getAtmById(final int id) {
    return atms
        .stream()
        .filter(atm -> atm.getId() == id)
        .findFirst();
  }

  /**
   * Возвращает банкомат с указанным идентификатором,
   * если он существует.
   *
   * @param id идентификатор искомого банкомата.
   * @return банкомат с указанным идентификатором,
   * если он существует.
   */
  public BankAtm getAtmIfExists(final int id) {
    return getAtmById(id)
        .orElseThrow(() -> new NoSuchElementException("atm was not found"));
  }

  /**
   * Изменяет банкомат офис с указанным идентификатором.
   *
   * @param id      идентификатор искомого банкомата.
   * @param newName новое название банкомата.
   */
  @Override
  public void updateAtm(final int id, final String newName) {
    BankAtm atmToUpdate = getAtmIfExists(id);

    Utils.validateNewName(newName, atmToUpdate.getName());

    atmToUpdate.setName(newName);
  }

  /**
   * Меняет значение флага доступности внесения денег
   * в банкомат на противоположный.
   *
   * @param atm банкомат, у которого меняется
   *            значение флага доступности внесения
   *            денег на противоположный.
   */
  @Override
  public void switchIsDepositAvailable(final BankAtm atm) {
    atm.setDepositAvailable(!atm.isDepositAvailable());
  }

  /**
   * Меняет значение флага доступности вывода денег
   * из банкомата на противоположный.
   *
   * @param atm банкомат, у которого меняется
   *            значение флага доступности вывода
   *            денег на противоположный.
   */
  @Override
  public void switchIsWithdrawAvailable(final BankAtm atm) {
    atm.setDepositAvailable(!atm.isWithdrawAvailable());
  }

  /**
   * Вносит деньги в банкомат.
   *
   * @param atm                  банкомат, в который вносятся деньги;
   * @param moneyAmountToDeposit сумма денег, вносимая в банкомат.
   */
  @Override
  public void depositMoney(
      final BankAtm atm,
      final BigDecimal moneyAmountToDeposit
  ) {
    if (atm.isDepositAvailable()) {
      if (moneyAmountToDeposit.compareTo(BigDecimal.ZERO) > 0) {
        atm.incMoneyAmount(moneyAmountToDeposit);
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
   * Выводит деньги из банкомата.
   *
   * @param atm                   банкомат, из которого выводятся деньги;
   * @param moneyAmountToWithdraw сумма денег, выводимая из банкомата.
   */
  public void withdrawMoney(
      final BankAtm atm,
      final BigDecimal moneyAmountToWithdraw
  ) {
    if (atm.isWithdrawAvailable()) {
      if (moneyAmountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException(
            "money amount to withdraw must be greater than zero"
        );
      }

      if (moneyAmountToWithdraw.compareTo(atm.getMoneyAmount()) > 0) {
        throw new IllegalArgumentException(
            "money amount to withdraw must less or equal to zero"
        );
      } else {
        atm.incMoneyAmount(moneyAmountToWithdraw.negate());
      }
    } else {
      throw new IllegalStateException("withdraw is not available");
    }
  }

  /**
   * Возвращает значение 'истина', если банковский офис
   * имеет хотя бы один связанный с ним банкомат,
   * в противном случае - 'ложь'.
   *
   * @param id идентификатор искомого банковского офиса.
   * @return 'истина' или 'ложь'.
   */
  @Override
  public boolean doesOfficeHaveAnyAssociatedAtm(
      final int id
  ) {
    List<BankAtm> atms = getAllAtms();

    for (BankAtm atm : atms) {
      if (atm.getOffice() != null &&
          atm.getOffice().getId() == id) {
        return true;
      }
    }

    return false;
  }

  /**
   * Удаляет банкомат с указанным идентификатором.
   *
   * @param id идентификатор искомого банкомата.
   */
  @Override
  public void deleteAtm(final int id) {
    BankAtm atmToDelete = getAtmIfExists(id);

    bankService.removeAtm(atmToDelete.getBank(), atmToDelete);

    atms.remove(atmToDelete);
  }
}
