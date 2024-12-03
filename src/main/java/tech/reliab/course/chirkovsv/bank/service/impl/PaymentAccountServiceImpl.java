package main.java.tech.reliab.course.chirkovsv.bank.service.impl;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.CreditAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.PaymentAccount;
import main.java.tech.reliab.course.chirkovsv.bank.entity.User;
import main.java.tech.reliab.course.chirkovsv.bank.service.CreditAccountService;
import main.java.tech.reliab.course.chirkovsv.bank.service.PaymentAccountService;
import main.java.tech.reliab.course.chirkovsv.bank.service.UserService;
import main.java.tech.reliab.course.chirkovsv.bank.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PaymentAccountServiceImpl implements PaymentAccountService {
  private static int nPaymentAccounts = 0;
  private final List<PaymentAccount> paymentAccounts = new ArrayList<>();

  private final UserService userService;
  private final CreditAccountService creditAccountService;

  public PaymentAccountServiceImpl(
      UserService userService,
      CreditAccountService creditAccountService) {
    this.userService = userService;
    this.creditAccountService = creditAccountService;
  }

  /**
   * Создаёт платёжный счёт.
   *
   * @param bank        банк, в котором взят
   *                    кредит.
   * @param user        пользователь онлайн-банка,
   *                    на которого оформлен
   *                    платёжный счёт.
   * @param moneyAmount количество денег на платёжном
   *                    счёте.
   * @return созданный платёжный счёт.
   */
  @Override
  public PaymentAccount createPaymentAccount(
      final Bank bank,
      final User user,
      final BigDecimal moneyAmount
  ) {
    PaymentAccount paymentAccountToCreate = new PaymentAccount(
        bank,
        user,
        moneyAmount
    );

    paymentAccountToCreate.setId(nPaymentAccounts++);

    user.addPaymentAccount(paymentAccountToCreate);

    paymentAccounts.add(paymentAccountToCreate);

    return paymentAccountToCreate;
  }

  /**
   * Возвращает список всех платёжных счетов.
   *
   * @return список всех платёжных счетов.
   */
  @Override
  public List<PaymentAccount> getAllPaymentAccounts() {
    return new ArrayList<>(paymentAccounts);
  }

  /**
   * Возвращает платёжный счёт с указанным идентификатором.
   *
   * @param id идентификатор искомого платёжного счёта.
   * @return платёжный счёт с указанным идентификатором.
   */
  @Override
  public Optional<PaymentAccount> getPaymentAccountById(final int id) {
    return paymentAccounts
        .stream()
        .filter(paymentAccount -> paymentAccount.getId() == id)
        .findFirst();
  }

  /**
   * Возвращает платёжный счёт с указанным идентификатором,
   * если он существует.
   *
   * @param id идентификатор искомого платёжного счёта.
   * @return платёжный счёт с указанным идентификатором, если
   * он существует.
   */
  private PaymentAccount getPaymentAccountIfExists(final int id) {
    return getPaymentAccountById(id)
        .orElseThrow(() -> new NoSuchElementException(
            "payment account was not found")
        );
  }

  /**
   * Изменяет платёжный аккаунт с указанным идентификатором
   *
   * @param id      идентификатор искомого платёжного аккаунта.
   * @param newBank новый банк, к которому относится
   *                платёжный счёт;
   * @param newUser новый пользователь онлайн-банка,
   *                на которого оформлен платёжный счёт.
   */
  @Override
  public void updatePaymentAccount(
      final int id,
      final Bank newBank,
      final User newUser
  ) {
    PaymentAccount paymentAccountToUpdate = getPaymentAccountIfExists(id);

    if (newUser != null) {
      if (newUser != paymentAccountToUpdate.getUser()) {
        paymentAccountToUpdate.setUser(newUser);

        newUser.addPaymentAccount(paymentAccountToUpdate);
      } else {
        throw new IllegalArgumentException(
            "new user must not be the same"
        );
      }
    }

    if (newBank != null) {
      Utils.updateAccountBank(paymentAccountToUpdate, userService, newBank);
    }
  }

  /**
   * Изменяет платёжный аккаунт с указанным идентификатором
   *
   * @param id      идентификатор искомого платёжного аккаунта.
   * @param newBank новый банк, к которому относится
   *                платёжный счёт;
   */
  @Override
  public void updatePaymentAccount(
      final int id,
      final Bank newBank
  ) {
    updatePaymentAccount(id, newBank, null);
  }

  /**
   * Изменяет платёжный аккаунт с указанным идентификатором
   *
   * @param id      идентификатор искомого платёжного аккаунта.
   * @param newUser новый пользователь онлайн-банка,
   *                на которого оформлен платёжный счёт.
   */
  @Override
  public void updatePaymentAccount(
      final int id,
      final User newUser
  ) {
    updatePaymentAccount(id, null, newUser);
  }

  /**
   * Возвращает значение 'истина', если платёжный счёт
   * имеет хотя бы один связанный с ним кредитный счёт,
   * в противном случае - 'ложь'.
   *
   * @param id идентификатор искомого платёжного счёта.
   * @return 'истина' или 'ложь'.
   */
  private boolean doesPaymentAccountHaveAnyAssociatedCreditAccount(
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
   * Удаляет платёжный аккаунт с указанным идентификатором.
   *
   * @param id идентификатор искомого платёжного аккаунта.
   */
  @Override
  public void deletePaymentAccount(final int id) {
    PaymentAccount paymentAccountToDelete = getPaymentAccountIfExists(id);

    if (doesPaymentAccountHaveAnyAssociatedCreditAccount(id)) {
      throw new IllegalStateException(
          "payment account has at least one associated credit account"
      );
    }

    paymentAccountToDelete.getUser().removePaymentAccount(paymentAccountToDelete);;

    paymentAccounts.remove(paymentAccountToDelete);
  }
}