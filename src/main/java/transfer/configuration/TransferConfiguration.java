package transfer.configuration;

import transfer.domain.TransferService;

public class TransferConfiguration {

    public static TransferService transferService() {
        return new TransferService(AccountConfiguration.accountRepository());
    }

}
