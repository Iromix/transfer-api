package transfer.domain

import spock.lang.Specification
import transfer.configuration.AccountConfiguration
import transfer.configuration.TransferConfiguration
import transfer.domain.Account
import transfer.domain.Money
import transfer.domain.TransferService
import transfer.domain.dto.MoneyDto

class TransferServiceSpec extends Specification {

    TransferService transferService
    AccountService accountService

    def setup() {
        transferService = new TransferConfiguration().transferService()
        accountService = new AccountConfiguration().accountService()
    }

    def "should fail to transfer money to not existing account"() {
        given: "only one account created"
        Account fromAccount = accountService.createAccount("EUR")
        Integer notExistingAccountNumber = 666
        MoneyDto moneyToTransfer = new MoneyDto(20.0, "EUR")

        when:
        transferService.transfer(fromAccount.getAccountNumber(), notExistingAccountNumber, moneyToTransfer)

        then: "raise exception"
        thrown NoSuchElementException
    }
}
