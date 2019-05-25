package transfer;

public class Account {
    private Integer accountNumber;
    private Money balance;

    Account(Integer accountNumber, Money balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    Account(Integer accountNumber) {
        this(accountNumber, new Money());
    }

    void withdraw(Money amountToWithdraw) {
        if (balance.lessThan(amountToWithdraw))
            throw new IllegalArgumentException("Cannot withdraw more money that it is available on account");
        balance = balance.subtract(amountToWithdraw);
    }

    void deposit(Money amountToDeposit) {
        if (!amountToDeposit.greaterThan(Money.ZERO))
            throw new IllegalArgumentException("Can deposit only positive amount");
        balance = balance.add(amountToDeposit);
    }

    public Money getBalance() {
        return balance;
    }
}
