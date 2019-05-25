package transfer;

public interface AccountRepository {
    void save(Account account);
    Account load(Account account);
}
