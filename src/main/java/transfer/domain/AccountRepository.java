package transfer.domain;

public interface AccountRepository {
    void save(Account account);
    Account load(Integer accountNumber);
    Account create(String currencyCode);
}
