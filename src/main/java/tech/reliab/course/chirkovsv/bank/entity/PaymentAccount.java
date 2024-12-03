package main.java.tech.reliab.course.chirkovsv.bank.entity;

import java.util.Objects;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

// сущность "платёжный счёт".
@Getter
@Setter
public class PaymentAccount extends Account {
  private int id;
  private BigDecimal moneyAmount = BigDecimal.ZERO;

  public PaymentAccount(
      final Bank bank,
      final User user,
      final BigDecimal moneyAmount
  ) {
    super(bank, user);

    this.moneyAmount = moneyAmount;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    PaymentAccount paymentAccount = (PaymentAccount) obj;

    return id == paymentAccount.id &&
        super.getBank().equals(paymentAccount.getBank());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, super.getBank());
  }

  @Override
  public String toString() {
    return "PaymentAccount {\n  id = " + id +
      "\n  userId = " + super.getUser().getId() +
      "\n, userFullName = " + super.getUser().getFullName() +
      "\n, bankName = " + super.getBank().getName() +
      "\n, moneyAmount = " + String.format("%.2f", moneyAmount) +
      "\n};\n";
  }
}