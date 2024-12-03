package main.java.tech.reliab.course.chirkovsv.bank.service;

import main.java.tech.reliab.course.chirkovsv.bank.entity.Bank;
import main.java.tech.reliab.course.chirkovsv.bank.entity.BankOffice;
import main.java.tech.reliab.course.chirkovsv.bank.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
  Employee createEmployee(
      final Bank bank,
      final String fullName,
      final LocalDate birthDate,
      final String post,
      final BankOffice office,
      final boolean hasAbilityToRegisterCredits,
      final BigDecimal salary
  );

  List<Employee> getAllEmployees();

  Optional<Employee> getEmployeeById(final int id);

  void updateEmployee(final int id, final String newFullName);

  void addOffice(final Employee employee, final BankOffice officeToAdd);

  void removeOffice(final Employee employee);

  void switchHasAbilityToRegisterCredits(final Employee employee);

  void deleteEmployee(final int id);
}