package transfer.domain

import spock.lang.Specification

class MoneySpec extends Specification {

    static final Currency USD = Currency.getInstance("USD")
    static final Currency PLN = Currency.getInstance("PLN")

    def "should add default currencies"() {
        when:
        Money moneyResult = new Money(2.10).add(new Money(3.10))

        then:
        new Money(5.20) == moneyResult
    }

    def "should add non default currencies"() {
        when:
        Money moneyResult = new Money(2.10, USD).add(new Money(3.10, USD))

        then:
        new Money(5.20, USD) == moneyResult
    }

    def "same value but different currencies should not be the same"() {
        expect:
        new Money(2.20, USD) != new Money(2.20, PLN)
    }

    def "should fail subtract money with different currencies"() {
        when:
        new Money(2.10, USD).subtract(new Money(1.10, PLN))

        then:
        thrown CurrencyMismatchException
    }

    def "should fail add money with different currencies"() {
        when:
        new Money(2.10, USD).add(new Money(1.10, PLN))

        then:
        thrown CurrencyMismatchException
    }

    def "should fail to create money with not available money"() {
        when:
        new Money(2.10, "XXXXXXX")

        then:
        thrown CurrencyMismatchException
    }
}
