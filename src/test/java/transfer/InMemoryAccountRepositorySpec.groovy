package transfer

import spock.lang.Specification

class InMemoryAccountRepositorySpec extends Specification {

    AccountRepository accountRepository

    def setup() {
        accountRepository = new InMemoryAccountRepository()
    }

    def "should save and load user from in memory database"() {
        Account account = new Account(1, new Money(10.0))
        when:
        accountRepository.save(account)
        Account loadedAccount = accountRepository.load(account.getAccountNumber())

        then:
        account == loadedAccount
    }

}
