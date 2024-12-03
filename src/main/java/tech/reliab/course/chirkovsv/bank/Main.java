package main.java.tech.reliab.course.chirkovsv.bank;

import main.java.tech.reliab.course.chirkovsv.bank.entity.*;

import main.java.tech.reliab.course.chirkovsv.bank.service.*;
import main.java.tech.reliab.course.chirkovsv.bank.service.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
  public static void main(String[] args) {
    BankService bankService = new BankServiceImpl();

    UserService userService = new UserServiceImpl(bankService);

    BankAtmService atmService = new BankAtmServiceImpl(bankService);

    CreditAccountService creditAccountService = new CreditAccountServiceImpl(userService);

    PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl(userService, creditAccountService);

    EmployeeService employeeService = new EmployeeServiceImpl(bankService, atmService, creditAccountService);

    BankOfficeService bankOfficeService = new BankOfficeServiceImpl(bankService, atmService, employeeService);

    Bank tBank = bankService.createBank("Т-Банк");

    System.out.println(tBank);

    BankOffice tBankOffice = bankOfficeService.createOffice(
        tBank,
        "головной офис",
        "г. Москва, Новый Арбат",
        true,
        true,
        true,
        true,
        new BigDecimal("500000"));

    System.out.println(tBankOffice);

    Employee employeeServingTBankAtm = employeeService.createEmployee(
        tBank,
        "Иванов Иван Иванович",
        LocalDate.of(1995, 1, 23),
        "операционист",
        tBankOffice,
        false,
        new BigDecimal("30000"));

    System.out.println(employeeServingTBankAtm);

    Employee employeeRegisteringTBankCredits = employeeService.createEmployee(
        tBank,
        "Алексеев Алексей Алексеевич",
        LocalDate.of(1990, 4, 8),
        "специалист по выдаче кредитов",
        null,
        true,
        new BigDecimal("60000"));

    System.out.println(employeeRegisteringTBankCredits);

    BankAtm tBankAtm = atmService.createAtm(
        tBankOffice,
        "банкомат 1",
        "1 этаж, холл",
        employeeServingTBankAtm,
        true,
        true,
        new BigDecimal("50000"));

    System.out.println(tBankAtm);

    User tBankUser = userService.createUser(
        "Дмитриев Дмитрий Дмитриевич",
        LocalDate.of(2000, 10, 21),
        "VK");

    System.out.println(tBankUser);

    PaymentAccount tBankUserPaymentAccount = paymentAccountService.createPaymentAccount(
        tBank,
        tBankUser,
        new BigDecimal("10000"));

    System.out.println(tBankUserPaymentAccount);

    CreditAccount tBankUserCreditAccount = creditAccountService.createCreditAccount(
        tBank,
        tBankUser,
        LocalDate.of(2024, 10, 9),
        36,
        new BigDecimal("60000"),
        new BigDecimal("15"),
        employeeRegisteringTBankCredits,
        tBankUserPaymentAccount);

    System.out.println(tBankUserCreditAccount);
  }
}