package transfer.configuration;

import transfer.domain.AccountRepository;
import transfer.domain.AccountService;
import transfer.domain.InMemoryAccountRepository;

public class AccountConfiguration {

    public static AccountService accountService() {
        return new AccountService(accountRepository());
    }

    static AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }
}
