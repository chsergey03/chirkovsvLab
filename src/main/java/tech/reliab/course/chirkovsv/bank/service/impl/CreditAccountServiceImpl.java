package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import main.java.tech.reliab.course.chirkovsv.bank.service.UserService;
import main.java.tech.reliab.course.chirkovsv.bank.service.CreditAccountService;
import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CreditAccountServiceImpl implements CreditAccountService {
  private static int nCreditAccounts = 0;
  private final List<CreditAccount> creditAccounts = new ArrayList<>();

  private final UserService userService;

  public CreditAccountServiceImpl(UserService userService) {
    this.userService = userService;
  }

  /**
   * Создаёт кредитный счёт.
   *
   * @param bank                   банк, в котором взят кредит;
   * @param user                   пользователь онлайн-банка на
   *                               которого оформлен кредит;
   * @param startDate              дата начала кредита;
   * @param nMonths                количество месяцев, на которые
   *                               рассчитан кредит;
   * @param moneyAmount            сумма кредита;
   * @param interestRate           значение процентной ставки;
   * @param employeeProvidedCredit работник банка, выдавший кредит;
   * @param paymentAccount         платёжный счёт, с которого будет
   *                               выплачиваться кредит.
   * @return созданный кредитный счёт.
   */
  @Override
  public CreditAccount createCreditAccount(
      final Bank bank,
      final User user,
      final LocalDate startDate,
      final int nMonths,
      final BigDecimal moneyAmount,
      final BigDecimal interestRate,
      final Employee employeeProvidedCredit,
      final PaymentAccount paymentAccount
  ) {
    CreditAccount creditAccountToCreate = new CreditAccount(
        bank,
        user,
        startDate,
        nMonths,
        employeeProvidedCredit,
        paymentAccount
    );

    creditAccountToCreate.setId(nCreditAccounts++);
    creditAccountToCreate.setEndDate(getCalculatedEndDate(startDate, nMonths));
    creditAccountToCreate.setMoneyAmount(getChosenMoneyAmount(moneyAmount, bank));
    creditAccountToCreate.setInterestRate(getChosenInterestRate(interestRate, bank));

    creditAccountToCreate.setMonthlyPayment(
        getCalculatedMonthlyPayment(
            creditAccountToCreate.getMoneyAmount(),
            creditAccountToCreate.getNMonths(),
            creditAccountToCreate.getInterestRate())
    );

    user.addCreditAccount(creditAccountToCreate);

    creditAccounts.add(creditAccountToCreate);

    return creditAccountToCreate;
  }

  /**
   * Возвращает вычисленную дату окончания
   * кредита.
   *
   * @param startDate дата начала кредита;
   * @param nMonths   количество месяцев, на
   *                  которые рассчитан кредит.
   * @return вычисленная дата окончания кредита.
   */
  private LocalDate getCalculatedEndDate(
      final LocalDate startDate,
      final int nMonths
  ) {
    return startDate.plusMonths(nMonths);
  }

  /**
   * Возвращает выбранную сумму кредита.
   *
   * @param moneyAmount запрашиваемая сумма
   *                    кредита;
   * @param bank        банк, в котором взят
   *                    кредит.
   * @return выбранная сумма кредита.
   */
  private BigDecimal getChosenMoneyAmount(
      final BigDecimal moneyAmount,
      final Bank bank) {
    if (Utils.isBigDecimalMoreThanUpperBound(
        moneyAmount,
        bank.getTotalMoneyAmount())
    ) {
      return bank.getTotalMoneyAmount();
    } else {
      return moneyAmount;
    }
  }

  /**
   * Возвращает выбранное значение процентной
   * ставки по кредиту.
   *
   * @param interestRate запрашиваемое значение
   *                     процентной ставки;
   * @param bank         банк, в котором взят
   *                     кредит.
   * @return выбранное значение процентной ставки
   * по кредиту.
   */
  private BigDecimal getChosenInterestRate(
      final BigDecimal interestRate,
      final Bank bank) {
    if (Utils.isBigDecimalMoreThanUpperBound(
        interestRate,
        bank.getInterestRate())
    ) {
      return bank.getInterestRate();
    } else {
      return interestRate;
    }
  }

  /**
   * Возвращает вычисленное значение ежемесячного
   * платежа по кредиту.
   *
   * @param moneyAmount  сумма кредита;
   * @param nMonths      количество месяцев, на
   *                     которые рассчитан кредит.
   * @param interestRate значение процентной ставки
   *                     по кредиту.
   * @return вычисленное значение ежемесячного
   * платежа по кредиту.
   */
  private BigDecimal getCalculatedMonthlyPayment(
      final BigDecimal moneyAmount,
      final int nMonths,
      final BigDecimal interestRate
  ) {
    BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

    if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
      return moneyAmount.divide(BigDecimal.valueOf(nMonths), 2, RoundingMode.HALF_UP);
    }

    BigDecimal numerator = monthlyRate.multiply((BigDecimal.ONE.add(monthlyRate)).pow(nMonths));

    BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(nMonths).subtract(BigDecimal.ONE);

    return moneyAmount.multiply(numerator).divide(denominator, 2, RoundingMode.HALF_UP);
  }

  /**
   * Возвращает список всех кредитных счетов.
   *
   * @return список всех кредитных счетов.
   */
  @Override
  public List<CreditAccount> getAllCreditAccounts() {
    return new ArrayList<>(creditAccounts);
  }

  /**
   * Возвращает кредитный счёт с указанным идентификатором.
   *
   * @param id идентификатор искомого кредитного счёта.
   * @return кредитный счёт с указанным идентификатором.
   */
  @Override
  public Optional<CreditAccount> getCreditAccountById(
      final int id
  ) {
    return creditAccounts
        .stream()
        .filter(creditAccount -> creditAccount.getId() == id)
        .findFirst();
  }

  /**
   * Возвращает кредитный счёт с указанным идентификатором,
   * если он существует.
   *
   * @param id идентификатор искомого кредитного счёта.
   * @return кредитный счёт с указанным идентификатором,
   * если он существует.
   */
  private CreditAccount getCreditAccountIfExists(
      final int id
  ) {
    return getCreditAccountById(id)
        .orElseThrow(() -> new NoSuchElementException(
            "credit account was not found")
        );
  }

  /**
   * Изменяет кредитный счёт с указанным
   * идентификатором.
   *
   * @param id      идентификатор искомого кредитного
   *                счёта;
   * @param newBank новый банк, к которому относится
   *                кредитный счёт;
   * @param newUser новый пользователь онлайн-банка,
   *                на которого оформлен кредитный счёт.
   */
  @Override
  public void updateCreditAccount(
      final int id,
      final Bank newBank,
      final User newUser
  ) {
    CreditAccount creditAccountToUpdate = getCreditAccountIfExists(id);

    if (newUser != null) {
      if (newUser != creditAccountToUpdate.getUser()) {
        creditAccountToUpdate.setUser(newUser);

        newUser.addCreditAccount(creditAccountToUpdate);
      } else {
        throw new IllegalArgumentException(
            "new user must not be the same"
        );
      }
    }

    if (newBank != null) {
      Utils.updateAccountBank(creditAccountToUpdate, userService, newBank);
    }

  }

  /**
   * Изменяет кредитный счёт с указанным
   * идентификатором.
   *
   * @param id      идентификатор искомого кредитного
   *                счёта;
   * @param newBank новый банк, к которому относится
   *                кредитный счёт.
   */
  @Override
  public void updateCreditAccount(
      final int id,
      final Bank newBank
  ) {
    updateCreditAccount(id, newBank, null);
  }

  /**
   * Изменяет кредитный счёт с указанным
   * идентификатором.
   *
   * @param id      идентификатор искомого кредитного
   *                счёта;
   * @param newUser новый пользователь онлайн-банка,
   *                на которого оформлен кредитный счёт.
   */
  @Override
  public void updateCreditAccount(
      final int id,
      final User newUser
  ) {
    updateCreditAccount(id, null, newUser);
  }

  /**
   * Удаляет кредитный счёт с указанным идентификатором.
   *
   * @param id идентификатор искомого кредитного счёта.
   */
  @Override
  public void deleteCreditAccount(final int id) {
    CreditAccount creditAccountToDelete = getCreditAccountIfExists(id);

    creditAccountToDelete.getUser().removeCreditAccount(creditAccountToDelete);

    creditAccounts.remove(creditAccountToDelete);
  }
}
