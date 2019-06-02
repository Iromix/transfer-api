package transfer.domain

import spock.lang.Specification
import transfer.configuration.AccountConfiguration
import transfer.configuration.TransferConfiguration
import transfer.domain.dto.AccountDto
import transfer.domain.dto.MoneyDto

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class TransferInMultiThreadedEnvironmentSpec extends Specification {

    TransferService transferService
    AccountService accountService

    def setup() {
        transferService = new TransferConfiguration().transferService()
        accountService = new AccountConfiguration().accountService()
    }

    def "simulate transfers between two accounts invoked by multiple systems"() {

        given: "two accounts with EUR currencies"
        Account fromAccount = accountService.createAccount("EUR")
        Account destinationAccount = accountService.createAccount("EUR")

        and: "source account has deposited money to transfer"
        double totalAmountToTransfer = 10_000
        accountService.deposit(fromAccount.getAccountNumber(), new MoneyDto(totalAmountToTransfer, "EUR"))

        when: "make many transfers up to total amount by many threads/systems"
        makeTransfersByMultipleSystems(fromAccount, destinationAccount, totalAmountToTransfer, 10.0)

        then: "all amount has been transferred"
        AccountDto account = accountService.getAccount(destinationAccount.getAccountNumber())
        account.getMoneyDto().amount == totalAmountToTransfer
    }

    def makeTransfersByMultipleSystems(fromAccount, destinationAccount, double totalAmountToTransfer, double oneTransferAmount) {
        ExecutorService executorService = Executors.newFixedThreadPool(20)
        for (int i = 0; i < totalAmountToTransfer / oneTransferAmount; i++) {
            executorService.execute({
                try {
                    transferService.transfer(
                            fromAccount.getAccountNumber(),
                            destinationAccount.getAccountNumber(),
                            new MoneyDto(oneTransferAmount, "EUR")
                    )
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
