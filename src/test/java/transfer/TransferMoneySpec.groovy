package transfer

import spock.lang.Specification

class TransferMoneySpec extends Specification {

    def "transfer money between two accounts"() {
        given: "two accounts - source account with 20 EUR, destination account with 0 EUR"
        TransferSerice transferService = new TransferSerice()
        Account fromAccount = new Account(1234, new Money(20.0))
        Account destinationAccount = new Account(111, Money.ZERO)
        Money transferedAmount = new Money(20.0, Money.DEFAULT_CURRENCY)

        when:
        transferService.transfer(fromAccount, destinationAccount, transferedAmount)

        then:
        fromAccount.getBalance() == Money.ZERO
        destinationAccount.getBalance() == transferedAmount
    }
}
