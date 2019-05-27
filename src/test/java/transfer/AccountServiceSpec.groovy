package transfer

import spock.lang.Specification
import transfer.dto.AccountDto
import transfer.dto.MoneyDto

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
}
