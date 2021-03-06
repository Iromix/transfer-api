package transfer.domain

import spock.lang.Specification
import transfer.domain.Account
import transfer.domain.Money

class AccountSpec extends Specification {

    static final Currency USD = Currency.getInstance("USD")
    static final Currency PLN = Currency.getInstance("PLN")

    def "should fail to withdraw more money than there is in balance"() {
        given:
        Account account = new Account(111, new Money(10.0, PLN))

        when:
        account.withdraw(new Money(20.0, PLN))

        then:
        thrown TransferException
    }

    def "should deposit money on account"() {
        given:
        Account account = new Account(111, new Money(10.0, PLN))

        when:
        account.deposit(new Money(10.0, PLN))

        then:
        account.getBalance() == new Money(20.0, PLN)
    }

    def "should fail to deposit negative amount of money"() {
        given:
        Account account = new Account(111, new Money(0.0, PLN))

        when:
        account.deposit(new Money(-10.0, PLN))

        then:
        thrown TransferException
    }
}
