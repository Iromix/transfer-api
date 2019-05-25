package transfer;

import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryAccountRepository implements AccountRepository{

    private ConcurrentHashMap<Integer, Account> map = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        requireNonNull(account);
        map.put(account.getAccountNumber(), account);
    }

    @Override
    public Account load(Account account) {
        return map.get(account.getAccountNumber());
    }
}
