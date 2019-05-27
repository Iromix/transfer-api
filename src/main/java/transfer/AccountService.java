package transfer;

import transfer.dto.AccountDto;
import transfer.dto.MoneyDto;

import java.util.Currency;
import java.util.List;

public class AccountService {

    private AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String currencyCode) {
        if (!currencyCodeIsAvailable(currencyCode))
            throw new IllegalArgumentException("Currency mismatch");
        return accountRepository.create(currencyCode);
    }

    public void deposit(Integer accountNumber, MoneyDto moneyDto) {
        Account account = accountRepository.load(accountNumber);
        account.deposit(new Money(moneyDto.getAmount(), moneyDto.getCurrencyCode()));
        accountRepository.save(account);
    }

    private boolean currencyCodeIsAvailable(String currencyCode) {
        return Currency.getAvailableCurrencies().contains(Currency.getInstance(currencyCode));
    }

    public List<Account> listAccounts() {
        return accountRepository.fetchAllAccounts();
    }

    public AccountDto getAccount(Integer accountNumber) {
        return createAccountDto(accountRepository.load(accountNumber));

    }

    private AccountDto createAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setMoneyDto(createMoneyDto(account.getBalance()));
        return accountDto;
    }

    private MoneyDto createMoneyDto(Money money) {
        MoneyDto moneyDto = new MoneyDto();
        moneyDto.setCurrencyCode(money.getCurrencyCode());
        moneyDto.setAmount(money.getValue());
        return moneyDto;
    }

}
