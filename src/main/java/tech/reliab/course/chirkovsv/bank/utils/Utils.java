package main.java.tech.reliab.course.chirkovsv.bank.utils;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Account;
import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankOffice;

import main.java.tech.reliab.course.chirkovsv.bank.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Objects;

import java.util.Random;

public class Utils {
  public static final int INC_DEFAULT_VALUE = 1;

  private static final Random generator = new Random();

  /**
   * Возвращает случайно сгенерированное вещественное значение
   * типа BigDecimal на отрезке [min, max].
   *
   * @param min минимальное значение отрезка;
   * @param max максимальное число отрезка.
   * @return случайно сгенерированное вещественное значение
   * типа BigDecimal на отрезке [min, max].
   */
  public static BigDecimal getBigDecimalGeneratedOnTheSegment(
      final BigDecimal min,
      final BigDecimal max) {
    return min.add(BigDecimal.valueOf(generator.nextDouble()).multiply(max.subtract(min)));
  }

  /**
   * Возвращает случайно сгенерированное вещественное значение
   * типа BigDecimal на отрезке [0, max].
   *
   * @param max максимальное число отрезка.
   * @return случайно сгенерированное вещественное значение
   * типа BigDecimal на отрезке [0, max].
   */
  public static BigDecimal getBigDecimalGeneratedOnTheSegment(
      final BigDecimal max) {
    BigDecimal min = new BigDecimal("0");

    return getBigDecimalGeneratedOnTheSegment(min, max);
  }

  /**
   * Возвращает значение 'истина', если вещественное значение
   * типа BigDecimal больше верхней границы.
   *
   * @param value      проверяемое вещественное значение;
   * @param upperBound значение верхней границы.
   * @return 'истина' или 'ложь'.
   */
  public static boolean isBigDecimalMoreThanUpperBound(
      final BigDecimal value,
      final BigDecimal upperBound) {
    return value.compareTo(upperBound) > 0;
  }

  /**
   * Возвращает значение 'истина', если вещественное значение
   * типа BigDecimal меньше нижней границы.
   *
   * @param value      проверяемое вещественное значение;
   * @param lowerBound значение нижней границы.
   * @return 'истина' или 'ложь'.
   */
  public static boolean isBigDecimalLessThanLowerBound(
      final BigDecimal value,
      final BigDecimal lowerBound) {
    return value.compareTo(lowerBound) < 0;
  }

  /**
   * Возвращает значение 'истина', если вещественное значение
   * типа BigDecimal больше нуля, в противном случае - 'ложь'.
   *
   * @param value проверяемое вещественное значение.
   * @return 'истина' или 'ложь'.
   */
  public static boolean isBigDecimalMoreThanZero(final BigDecimal value) {
    return isBigDecimalMoreThanUpperBound(value, BigDecimal.ZERO);
  }

  /**
   * Возвращает значение 'истина', если вещественное значение
   * типа BigDecimal меньше нуля, в противном случае - 'ложь'.
   *
   * @param value проверяемое вещественное значение.
   * @return 'истина' или 'ложь'.
   */
  public static boolean isBigDecimalLessThanZero(final BigDecimal value) {
    return isBigDecimalLessThanLowerBound(value, BigDecimal.ZERO);
  }

  /**
   * Производит валидацию параметров обновления счётчика сущности
   * онлайн банка.
   *
   * @param parentEntity                родительская сущность;
   * @param entityAssociatedWithCounter сущность, связанная со
   *                                    счётчиком;
   * @param <T1>                        класс родительской сущности;
   * @param <T2>                        класс сущности, связанной
   *                                    со счётчиком родительской
   *                                    сущности.
   */
  public static <T1, T2> void validateParametersOnNonNullValues(
      final T1 parentEntity,
      final T2 entityAssociatedWithCounter
  ) {
    Objects.requireNonNull(
        parentEntity,
        "parent entity cannot be null"
    );

    Objects.requireNonNull(
        entityAssociatedWithCounter,
        "entity associated with counter cannot be null"
    );
  }

  /**
   * Производит валидацию ссылки на родительскую сущность
   *
   * @param parentEntity                     родительская сущность;
   * @param entityAssociatedWithCounter      сущность, связанная со
   *                                         счётчиком;
   * @param isNecessaryToReferToParentEntity флаг, определяющий должна
   *                                         ли сущность, связанная со
   *                                         счётчиком, ссылаться на
   *                                         родительскую сущность;
   * @param <T1>                             класс родительской сущности;
   * @param <T2>                             класс сущности, связанной
   *                                         со счётчиком родительской
   *                                         сущности.
   */
  public static <T1, T2> void validateReferringToParentEntity(
      final T1 parentEntity,
      final T2 entityAssociatedWithCounter,
      final boolean isNecessaryToReferToParentEntity
  ) {
    T1 parentEntityNecessaryValue = null;
    String exceptionMessage = "entity associated with counter ";

    if (isNecessaryToReferToParentEntity) {
      parentEntityNecessaryValue = parentEntity;
      exceptionMessage += "does not refer to parent entity";
    } else {
      exceptionMessage += "already refers to parent entity";
    }

    try {
      String methodName;
      Class<?> parentEntityType;

      if (parentEntity instanceof Bank) {
        methodName = "getBank";
        parentEntityType = Bank.class;
      } else if (parentEntity instanceof BankOffice) {
        methodName = "getOffice";
        parentEntityType = BankOffice.class;
      } else {
        throw new IllegalArgumentException("unsupported parent entity type");
      }

      var getParentEntityMethod = entityAssociatedWithCounter.getClass().getMethod(methodName);
      Object parentEntityValue = getParentEntityMethod.invoke(entityAssociatedWithCounter);

      if (parentEntityValue != parentEntityNecessaryValue) {
        throw new IllegalArgumentException(exceptionMessage);
      }
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalArgumentException(
          "entity associated with counter does not support the required method.",
          e
      );
    }
  }

  /**
   * Производит валидацию нового значения поля
   * "Имя" сущности (если такое поле имеется).
   *
   * @param newNameValue новое значения поля
   *                     "Имя" сущности.
   */
  public static void validateNewName(
      final String newNameValue,
      final String oldNameValue) {
    if (newNameValue.isEmpty()) {
      throw new IllegalArgumentException("new name cannot be empty");
    }

    if (newNameValue.equals(oldNameValue)) {
      throw new IllegalArgumentException("new name cannot be the same");
    }
  }

  /**
   * Возвращает случайное значение из перечисления
   *
   * @param enumClass класс перечисления;
   * @param <E>       тип элемента перечисления.
   * @return случайное значение из перечисления.
   */
  public static <E extends Enum<E>> E getRandomEnumValue(Class<E> enumClass) {
    E[] values = enumClass.getEnumConstants();

    return values[generator.nextInt(values.length)];
  }

  /**
   * Изменяет данные о банке у счёта.
   *
   * @param account     счёт в банке.
   * @param userService сервис пользователя
   *                    онлайн-банка;
   * @param newBank     новый банк, с которым
   *                    связан изменяемый счёт.
   */
  public static void updateAccountBank(
      final Account account,
      final UserService userService,
      final Bank newBank
  ) {
    if (newBank != account.getBank()) {
      account.setBank(newBank);

      if (!userService.doesUserHaveBank(
          account.getUser(),
          newBank.getId())
      ) {
        account.getUser().addBank(newBank);
      }
    } else {
      throw new IllegalArgumentException(
          "new bank must not be the same"
      );
    }
  }
}