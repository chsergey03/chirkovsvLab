package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import main.java.tech.reliab.course.chirkovsv.bank.service.BankAtmService;
import main.java.tech.reliab.course.chirkovsv.bank.service.BankService;
import main.java.tech.reliab.course.chirkovsv.bank.service.EmployeeService;
import main.java.tech.reliab.course.chirkovsv.bank.service.CreditAccountService;

import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
  private static int nEmployees = 0;
  private final List<Employee> employees = new ArrayList<>();

  private final BankService bankService;
  private final BankAtmService bankAtmService;
  private final CreditAccountService creditAccountService;

  public EmployeeServiceImpl(
      BankService bankService,
      BankAtmService bankAtmService,
      CreditAccountService creditAccountService) {
    this.bankService = bankService;
    this.bankAtmService = bankAtmService;
    this.creditAccountService = creditAccountService;
  }

  /**
   * создаёт работник банка.
   *
   * @param fullName                    Ф.И.О. работника;
   * @param birthDate                   дата рождения;
   * @param post                        занимаемая должность;
   * @param hasAbilityToRegisterCredits флаг, определяющий, имеет ли
   *                                    работник право выдавать кредиты;
   * @param salary                      размер заработной платы.
   * @return создаваемый работник банка.
   */
  @Override
  public Employee createEmployee(
      final Bank bank,
      final String fullName,
      final LocalDate birthDate,
      final String post,
      final BankOffice office,
      final boolean hasAbilityToRegisterCredits,
      final BigDecimal salary
  ) {
    Employee employeeToCreate = new Employee(
        fullName,
        birthDate,
        post,
        office,
        office != null,
        hasAbilityToRegisterCredits,
        salary
    );

    if (office != null && !office.getBank().equals(bank)) {
      throw new IllegalArgumentException(
          "bank of office and bank of employee are not the same"
      );
    }

    employeeToCreate.setId(nEmployees++);

    bankService.addEmployee(bank, employeeToCreate);

    employees.add(employeeToCreate);

    return employeeToCreate;
  }

  /**
   * Возвращает список со всеми работниками банков.
   *
   * @return список со всеми работниками банков.
   */
  @Override
  public List<Employee> getAllEmployees() {
    return new ArrayList<>(employees);
  }

  /**
   * Возвращает работника банка с указанным идентификатором.
   *
   * @param id идентификатор искомого работника банка.
   * @return работник банка с указанным идентификатором.
   */
  @Override
  public Optional<Employee> getEmployeeById(final int id) {
    return employees
        .stream()
        .filter(employee -> employee.getId() == id)
        .findFirst();
  }

  /**
   * Возвращает работника банка с указанным идентификатором,
   * если он существует.
   *
   * @param id идентификатор искомого работника банка.
   * @return работник банка с указанным идентификатором, если
   * он существует.
   */
  private Employee getEmployeeIfExists(final int id) {
    return getEmployeeById(id)
        .orElseThrow(() -> new NoSuchElementException("employee was not found"));
  }

  /**
   * Изменяет работника банка с указанным идентификатором.
   *
   * @param id идентификатор искомого работника;
   * @param newFullName новое Ф.И.О. работника.
   */
  @Override
  public void updateEmployee(final int id, final String newFullName) {
    Employee employeeToUpdate = getEmployeeIfExists(id);

    Utils.validateNewName(newFullName, employeeToUpdate.getFullName());

    employeeToUpdate.setFullName(newFullName);
  }

  /**
   * Добавляет работнику банка офис, в котором он
   * работает.
   *
   * @param employee    работник, которому добавляется
   *                    офис;
   * @param officeToAdd офис, добавляемый работнику
   *                    банка.
   */
  @Override
  public void addOffice(
      final Employee employee,
      final BankOffice officeToAdd) {
    if (employee.getOffice() != null) {
      throw new IllegalStateException(
          "employee already has an office"
      );
    }

    employee.setOffice(officeToAdd);

    switchDoesWorkInTheOffice(employee);
  }

  /**
   * Убирает офис у работника.
   */
  @Override
  public void removeOffice(final Employee employee) {
    if (employee.getOffice() == null) {
      throw new IllegalStateException(
          "employee already hasn't an office"
      );
    }

    employee.setOffice(null);

    switchDoesWorkInTheOffice(employee);
  }

  /**
   * Меняет значение флага, определяющего, работает ли работник
   * в офисе или нет, на противоположный.
   *
   * @param employee работник банка, у которого меняется значение
   *                 флага, определяющего, работает ли он в офисе
   *                 или нет, на противоположный.
   */
  private void switchDoesWorkInTheOffice(final Employee employee) {
    employee.setWorkInTheOffice(employee.isWorkInTheOffice());
  }

  /**
   * Меняет значение флага, определяющего, имеет ли работник право
   * выдавать кредиты или нет, на противоположный.
   *
   * @param employee работник банка, у которого меняется значение
   *                 флага, определяющего, имеет ли он право выдавать
   *                 кредиты или нет, на противоположный.
   */
  @Override
  public void switchHasAbilityToRegisterCredits(final Employee employee) {
    employee.setAbilityToRegisterCredits(employee.isAbilityToRegisterCredits());
  }

  /**
   * возвращает значение 'истина', если работник
   * банка имеет хотя бы один связанный с ним
   * банкомат, в противном случае - 'ложь'.
   *
   * @param id идентификатор искомого работника.
   * @return 'истина' или 'ложь'.
   */
  private boolean doesEmployeeHaveAnyAssociatedAtm(
      final int id
  ) {
    List<BankAtm> atms = bankAtmService.getAllAtms();

    for (BankAtm atm : atms) {
      if (atm.getServingEmployee() != null &&
          atm.getServingEmployee().getId() == id) {
        return true;
      }
    }

    return false;
  }

  /**
   * возвращает значение 'истина', если работник
   * банка имеет хотя бы один связанный с ним
   * кредит, в противном случае - 'ложь'.
   *
   * @param id идентификатор искомого работника.
   * @return 'истина' или 'ложь'.
   */
  private boolean doesEmployeeHaveAnyAssociatedCredit(
      final int id
  ) {
    List<CreditAccount> creditAccounts = creditAccountService.getAllCreditAccounts();

    for (CreditAccount creditAccount : creditAccounts) {
      if (creditAccount.getEmployeeProvidedCredit() != null &&
          creditAccount.getEmployeeProvidedCredit().getId() == id) {
        return true;
      }
    }

    return false;
  }

  /**
   * Удаляет работника банка с указанным идентификатором.
   *
   * @param id идентификатор искомого работника.
   */
  @Override
  public void deleteEmployee(final int id) {
    Employee employeeToDelete = getEmployeeIfExists(id);

    if (doesEmployeeHaveAnyAssociatedAtm(id)) {
      throw new IllegalStateException(
          "employee has at least one associated atm"
      );
    }

    if (doesEmployeeHaveAnyAssociatedCredit(id)) {
      throw new IllegalStateException(
          "employee has at least one associated credit"
      );
    }

    employees.remove(employeeToDelete);
  }
}