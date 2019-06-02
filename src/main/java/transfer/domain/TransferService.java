package transfer.domain;

import transfer.domain.dto.MoneyDto;

import static org.apache.commons.lang.Validate.notNull;

public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized void transfer(Integer accountFrom, Integer accountTo, MoneyDto moneyDto) {
        transfer(
                accountRepository.load(accountFrom),
                accountRepository.load(accountTo),
                new Money(moneyDto.getAmount(), moneyDto.getCurrencyCode())
        );
    }

    private void transfer(Account from, Account destination, Money amount) {
        notNull(from);
        notNull(destination);

        from.withdraw(amount);
        destination.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(destination);
    }

}
