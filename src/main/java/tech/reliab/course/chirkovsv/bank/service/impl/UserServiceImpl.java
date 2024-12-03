package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.CreditAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.PaymentAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.User;

import main.java.tech.reliab.course.chirkovsv.bank.service.BankService;
import main.java.tech.reliab.course.chirkovsv.bank.service.UserService;

import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
  public static final BigDecimal MONTHLY_INCOME_MAX_VALUE = new BigDecimal("10000");

  private static int nUsers = 0;
  private final List<User> users = new ArrayList<>();

  private final BankService bankService;

  public UserServiceImpl(BankService bankService) {
    this.bankService = bankService;
  }

  /**
   * Создаёт пользователя онлайн-банка.
   *
   * @param fullName  Ф.И.О. пользователя;
   * @param birthDate дата рождения
   * @param workplace место работы;
   * @return созданный пользователь онлайн-банка.
   */
  @Override
  public User createUser(
      final String fullName,
      final LocalDate birthDate,
      final String workplace
  ) {
    User userToCreate = new User(
        fullName,
        birthDate,
        workplace
    );

    userToCreate.setId(nUsers++);
    userToCreate.setMonthlyIncome(getGeneratedMonthlyIncome());

    userToCreate.setCreditRating(
        getCalculatedCreditRating(userToCreate.getMonthlyIncome())
    );

    users.add(userToCreate);

    return userToCreate;
  }

  /**
   * Возвращает сгенерированное значение месячного дохода
   * пользователя.
   *
   * @return сгенерированное значение месячного дохода
   * пользователя.
   */
  private BigDecimal getGeneratedMonthlyIncome() {
    return Utils.getBigDecimalGeneratedOnTheSegment(
        MONTHLY_INCOME_MAX_VALUE
    );
  }

  /**
   * Возвращает вычисленное значение кредитного рейтинга
   * пользователя.
   *
   * @param monthlyIncome размер месячного дохода пользователя.
   * @return вычисленное значение кредитного рейтинга
   * пользователя.
   */
  private BigDecimal getCalculatedCreditRating(
      final BigDecimal monthlyIncome
  ) {
    BigDecimal baseRating = BigDecimal.valueOf(100);
    BigDecimal stepIncrease = BigDecimal.valueOf(100);

    BigDecimal additionalSteps = monthlyIncome.divide(
        BigDecimal.valueOf(1000),
        0,
        RoundingMode.DOWN
    );

    return baseRating.add(
        additionalSteps.multiply(stepIncrease)
    );
  }

  /**
   * Возвращает список всех пользователей онлайн-банка.
   *
   * @return список всех пользователей онлайн-банка.
   */
  @Override
  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  /**
   * Возвращает пользователя онлайн-банка с
   * указанным идентификатором.
   *
   * @param id идентификатор искомого пользователя.
   * @return пользователь онлайн-банка с указанным
   * идентификатором.
   */
  @Override
  public Optional<User> getUserById(int id) {
    return users
        .stream()
        .filter(user -> user.getId() == id)
        .findFirst();
  }

  /**
   * Возвращает пользователя онлайн-банка с указанным
   * идентификатором, если он существует.
   *
   * @param id идентификатор искомого пользователя.
   * @return пользователь онлайн-банка с указанным
   * идентификатором, если он существует
   */
  private User getUserIfExists(int id) {
    return getUserById(id)
        .orElseThrow(
            () -> new NoSuchElementException("user was not found")
        );
  }

  /**
   * Изменяет пользователя онлайн-банка с указанным идентификатором.
   *
   * @param id          идентификатор искомого пользователя.
   * @param newFullName новое Ф.И.О. пользователя.
   */
  @Override
  public void updateUser(final int id, final String newFullName) {
    User userToUpdate = getUserIfExists(id);

    Utils.validateNewName(newFullName, userToUpdate.getFullName());

    userToUpdate.setFullName(newFullName);
  }

  /**
   * возвращает значение ''
   *
   * @param user
   * @param bankId
   * @return 'истина' или 'ложь'.
   */
  public boolean doesUserHaveBank(
      final User user,
      final int bankId) {
    List<Bank> banks = user.getBanks();

    for (Bank bank : banks) {
      if (bank != null && bank.getId() == bankId) {
        return true;
      }
    }

    return false;
  }

  /**
   * Удаляет пользователя онлайн-банка с
   * указанным идентификатором.
   *
   * @param id идентификатор искомого
   *           пользователя.
   */
  @Override
  public void deleteUser(final int id) {
    User userToDelete = getUserIfExists(id);

    if (!userToDelete.getBanks().isEmpty() ||
        !userToDelete.getCreditAccounts().isEmpty() ||
        !userToDelete.getPaymentAccounts().isEmpty()) {
      throw new IllegalStateException(
          "cannot delete user with associated entities."
      );
    }

    users.remove(userToDelete);
  }
}
