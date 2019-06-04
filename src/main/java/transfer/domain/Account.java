package transfer.domain;

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
            throw new TransferException("Cannot withdraw more money that it is available on account");
        balance = balance.subtract(amountToWithdraw);
    }

    void deposit(Money amountToDeposit) {
        if (!amountToDeposit.greaterThanZero())
            throw new TransferException("Can deposit only positive amount");
        balance = balance.add(amountToDeposit);
    }

    public Money getBalance() {
        return balance;
    }
}
