package transfer;

public class TransferService {

    private AccountRepository accountRepository;

    TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(Account from, Account destination, Money amount) {
        accountRepository.load(from);
        accountRepository.load(destination);

        from.withdraw(amount);
        destination.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(destination);
    }
}
