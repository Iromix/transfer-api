package transfer;

public class TransferSerice {

    public void transfer(Account from, Account destination, Money amount) {
        from.withdraw(amount);
        destination.deposit(amount);
    }
}
