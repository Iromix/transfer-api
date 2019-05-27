package transfer.domain

import spock.lang.Specification
import transfer.configuration.TransferConfiguration
import transfer.domain.Account
import transfer.domain.Money
import transfer.domain.TransferService

class TransferServiceSpec extends Specification {

    TransferService transferService

    def setup() {
        transferService = new TransferConfiguration().transferService()
    }

    def "transfer money between two accounts"() {
        given: "two accounts - source account with 20 EUR, destination account with 0 EUR"
        Account fromAccount = new Account(1234, new Money(20.0))
        Account destinationAccount = new Account(111, Money.ZERO)
        Money transferredAmount = new Money(20.0, Money.DEFAULT_CURRENCY)

        when:
        transferService.transfer(fromAccount, destinationAccount, transferredAmount)

        then:
        fromAccount.getBalance() == Money.ZERO
        destinationAccount.getBalance() == transferredAmount
    }
}
