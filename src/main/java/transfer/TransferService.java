package transfer;

import transfer.dto.MoneyDto;

import static org.apache.commons.lang.Validate.notNull;

public class TransferService {

    private AccountRepository accountRepository;

    TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(Integer accoutFrom, Integer accountTo, MoneyDto moneyDto) {
        System.out.println("transfer account " + accoutFrom + " account to " + accountTo);
        transfer(
                accountRepository.load(accoutFrom),
                accountRepository.load(accountTo),
                new Money(moneyDto.getAmount(), moneyDto.getCurrencyCode())
        );
    }

    void transfer(Account from, Account destination, Money amount) {
        notNull(from);
        notNull(destination);

        from.withdraw(amount);
        destination.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(destination);
    }

}
