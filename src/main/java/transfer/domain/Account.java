package transfer.domain;

import java.util.Objects;

class Account {

    private final Integer accountNumber;
    private Money balance;

    Account(Integer accountNumber, Money balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    void withdraw(Money amountToWithdraw) {
        if (balance.lessThan(amountToWithdraw))
            throw new IllegalArgumentException("Cannot withdraw more money that it is available on account");
        balance = balance.subtract(amountToWithdraw);
    }

    void deposit(Money amountToDeposit) {
        if (!amountToDeposit.greaterThanZero())
            throw new IllegalArgumentException("Can deposit only positive amount");
        balance = balance.add(amountToDeposit);
    }

    public Money getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance);
    }
}
