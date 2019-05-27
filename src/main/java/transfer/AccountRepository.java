package transfer;

import java.util.List;

public interface AccountRepository {
    void save(Account account);
    Account load(Integer accountNumber);
    Account create(String currencyCode);
    List<Account> fetchAllAccounts();
}
