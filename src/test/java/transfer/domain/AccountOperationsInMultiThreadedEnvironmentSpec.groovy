package transfer.domain

import spock.lang.Specification
import transfer.configuration.AccountConfiguration
import transfer.domain.dto.AccountDto
import transfer.domain.dto.MoneyDto

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AccountOperationsInMultiThreadedEnvironmentSpec extends Specification {

    AccountService accountService

    def setup() {
        accountService = new AccountConfiguration().accountService()
    }

    def "simulate deposits on account invoked by multiple systems"() {

        given: "account with deposited money"
        Account account = accountService.createAccount("EUR")
        double totalAmountToTransfer = 10_000
        accountService.deposit(
                account.getAccountNumber(),
                new MoneyDto(totalAmountToTransfer, "EUR")
        )

        when: "make many deposits and withdraws by multiple threads/systems"
        makeDepositsAndWithdrawsByMultipleSystems(account, totalAmountToTransfer, 10.0)

        then: "account balance is still the same"
        AccountDto resultAccount = accountService.getAccount(account.getAccountNumber())
        resultAccount.getMoneyDto().amount == totalAmountToTransfer
    }

    def makeDepositsAndWithdrawsByMultipleSystems(Account account, double totalAmountToTransfer, double oneTransferAmount) {
        ExecutorService executorService = Executors.newFixedThreadPool(20)
        MoneyDto money = new MoneyDto(oneTransferAmount, "EUR")
        for (int i = 0; i < totalAmountToTransfer / oneTransferAmount; i++) {
            executorService.execute({
                try {
                    accountService.deposit(account.getAccountNumber(), money)
                } catch (InterruptedException e) {
                    e.printStackTrace()
                }
            })
            executorService.execute({
                try {
                    accountService.withdraw(account.getAccountNumber(), money)
                } catch (InterruptedException e) {
                    e.printStackTrace()
                }
            })
        }
        awaitTerminationAfterShutdown(executorService)
    }

    def awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown()
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow()
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow()
            Thread.currentThread().interrupt()
        }
    }
}
