package transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

public class InMemoryAccountRepository implements AccountRepository{

    private static AtomicInteger nextAccountNumber = new AtomicInteger(0);
    private static ConcurrentHashMap<Integer, Account> map = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        requireNonNull(account);
        map.put(account.getAccountNumber(), account);
    }

    @Override
    public Account load(Integer accountNumber) {
        return map.get(accountNumber);
    }

    @Override
    public Account create(String currencyCode) {
        Account account = new Account(nextAccountNumber.incrementAndGet(), new Money(currencyCode));
        map.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public List<Account> fetchAllAccounts() {
        return new ArrayList<>(map.values());
    }
}
