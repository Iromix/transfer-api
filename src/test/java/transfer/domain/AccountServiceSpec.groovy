package transfer.domain

import spock.lang.Specification
import transfer.configuration.AccountConfiguration
import transfer.domain.dto.AccountDto
import transfer.domain.dto.MoneyDto

class AccountServiceSpec extends Specification {

    AccountService accountService

    def setup() {
        accountService = new AccountConfiguration().accountService()
    }

    def "create new account"() {
        given:
        String currencyCode = "PLN"

        when:
        Integer accountNumber = accountService.createAccount(currencyCode).getAccountNumber()
        AccountDto createdAccount = accountService.getAccount(accountNumber)

        then:
        createdAccount.getAccountNumber() == accountNumber
        createdAccount.getMoneyDto().getCurrencyCode() == currencyCode
    }

    def "deposit money on account"() {
        given:
        String currencyCode = "PLN"
        MoneyDto moneyToDeposit = new MoneyDto(20.0, currencyCode)
        Integer accountNumber = accountService.createAccount(currencyCode).getAccountNumber()

        when:
        accountService.deposit(accountNumber, moneyToDeposit)

        then:
        AccountDto accountWithNewDeposit = accountService.getAccount(accountNumber)
        accountWithNewDeposit.getMoneyDto().getAmount() == moneyToDeposit.getAmount()
    }

    def "withdraw money from account"() {
        given: "account with 20 PLN"
        MoneyDto money = new MoneyDto(20.0, "PLN")
        Integer accountNumber = accountService.createAccount("PLN").getAccountNumber()
        accountService.deposit(accountNumber, money)

        when:
        accountService.withdraw(accountNumber, money)

        then:
        AccountDto account = accountService.getAccount(accountNumber)
        account.getMoneyDto().getAmount() == 0
    }

    def "fail to withdraw more money that exists on account"() {
        given: "account with 0 money"
        Integer accountNumber = accountService.createAccount("PLN").getAccountNumber()

        when:
        accountService.withdraw(accountNumber, new MoneyDto(20.0, "PLN"))

        then:
        thrown TransferException
    }

    def "fail to deposit money in different currency"() {
        given:
        MoneyDto moneyToDeposit = new MoneyDto(20.0, "USD")
        Integer accountNumber = accountService.createAccount("PLN").getAccountNumber()

        when:
        accountService.deposit(accountNumber, moneyToDeposit)

        then:
        thrown CurrencyMismatchException
    }
}
