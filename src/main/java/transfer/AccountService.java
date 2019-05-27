package transfer;

import transfer.dto.AccountDto;
import transfer.dto.MoneyDto;

public class AccountService {

    private final AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository) {
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
        return createAccountDto(accountRepository.load(accountNumber));
    }

    private AccountDto createAccountDto(Account account) {
        return new AccountDto(account.getAccountNumber(), createMoneyDto(account.getBalance()));
    }

    private MoneyDto createMoneyDto(Money money) {
        return new MoneyDto(money.getValue(), money.getCurrencyCode());
    }
}
