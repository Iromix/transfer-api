package transfer.domain;

import java.util.NoSuchElementException;
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
        if (!map.containsKey(accountNumber))
            throw new NoSuchElementException("There is no account with this number");
        return map.get(accountNumber);
    }

    @Override
    public Account create(String currencyCode) {
        Account account = new Account(nextAccountNumber.incrementAndGet(), new Money(currencyCode));
        map.put(account.getAccountNumber(), account);
        return account;
    }

}
