package transfer;

public class TransferConfiguration {

    public static TransferService transferService() {
        return new TransferService(AccountConfiguration.accountRepository());
    }

}
