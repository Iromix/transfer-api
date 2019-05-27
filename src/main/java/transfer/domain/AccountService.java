package transfer.domain;

import io.javalin.NotFoundResponse;
import transfer.domain.dto.AccountDto;
import transfer.domain.dto.MoneyDto;

import static org.apache.commons.lang.Validate.notNull;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String currencyCode) {
        return accountRepository.create(currencyCode);
    }

    public void deposit(Integer accountNumber, MoneyDto moneyDto) {
        Account account = accountRepository.load(accountNumber);
        account.deposit(new Money(moneyDto.getAmount(), moneyDto.getCurrencyCode()));
        accountRepository.save(account);
    }

    public void withdraw(Integer accountNumber, MoneyDto moneyDto) {
        Account account = accountRepository.load(accountNumber);
        account.withdraw(new Money(moneyDto.getAmount(), moneyDto.getCurrencyCode()));
        accountRepository.save(account);
    }

    public AccountDto getAccount(Integer accountNumber) {
        Account account = accountRepository.load(accountNumber);
        return createAccountDto(account);
    }

    private AccountDto createAccountDto(Account account) {
        return new AccountDto(account.getAccountNumber(), createMoneyDto(account.getBalance()));
    }

    private MoneyDto createMoneyDto(Money money) {
        return new MoneyDto(money.getValue(), money.getCurrencyCode());
    }
}
