package transfer.domain

import spock.lang.Specification
import transfer.configuration.AccountConfiguration
import transfer.configuration.TransferConfiguration
import transfer.domain.dto.AccountDto
import transfer.domain.dto.MoneyDto

class TransferServiceSpec extends Specification {

    TransferService transferService
    AccountService accountService

    def setup() {
        transferService = new TransferConfiguration().transferService()
        accountService = new AccountConfiguration().accountService()
    }

    def "should transfer money between accounts"() {
        given: "only one account created"
        MoneyDto moneyToTransfer = new MoneyDto(20.0, "EUR")
        AccountDto fromAccount = accountService.createAccount("EUR")
        accountService.deposit(fromAccount.getAccountNumber(), moneyToTransfer)
        AccountDto destinationAccount = accountService.createAccount("EUR")

        when:
        transferService.transfer(fromAccount.getAccountNumber(), destinationAccount.getAccountNumber(), moneyToTransfer)

        then: "raise exception"
        AccountDto accountWithAddedMoney = accountService.getAccount(destinationAccount.getAccountNumber())
        accountWithAddedMoney.moneyDto.amount == moneyToTransfer.amount
    }

    def "should fail to transfer money to not existing account"() {
        given: "only one account created"
        AccountDto fromAccount = accountService.createAccount("EUR")
        Integer notExistingAccountNumber = 666
        MoneyDto moneyToTransfer = new MoneyDto(20.0, "EUR")

        when:
        transferService.transfer(fromAccount.getAccountNumber(), notExistingAccountNumber, moneyToTransfer)

        then: "raise exception"
        thrown NoSuchElementException
    }

    def "should fail to transfer money between accounts with different currencies"() {
        given: "two accounts with different currencies"
        MoneyDto moneyToTransfer = new MoneyDto(20.0, "EUR")
        AccountDto fromAccount = accountService.createAccount("EUR")
        accountService.deposit(fromAccount.getAccountNumber(), moneyToTransfer)
        AccountDto destinationAccount = accountService.createAccount("PLN")

        when:
        transferService.transfer(fromAccount.getAccountNumber(), destinationAccount.getAccountNumber(), moneyToTransfer)

        then: "raise currency mismatch exception"
        thrown CurrencyMismatchException
    }
}
