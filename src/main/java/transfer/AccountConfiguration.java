package transfer;

public class AccountConfiguration {

    public static AccountService accountService() {
        return new AccountService(accountRepository());
    }

    static AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }
}
