package transfer

import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class ApiSpec extends IntegrationSpec {

    @Shared Integer fromAccountNumber
    @Shared Integer destinationAccountNumber

    def "create accounts for transfer"() {
        when: "create first account"
        def response = client.post(
                requestContentType: "application/json",
                path: "/account/currency/PLN"
        )
        fromAccountNumber = response.responseData.accountNumber

        then:
        response.status == 201

        when: "create second account"
        def secondResponse = client.post(
                requestContentType: "application/json",
                path: "/account/currency/PLN"
        )
        destinationAccountNumber = secondResponse.responseData.accountNumber

        then:
        secondResponse.status == 201
    }

    def "deposit money on account before transfer"() {
        given:
        def depositRequestBody = [amount: 10.0, currencyCode: "PLN"]

        when: "deposit money on first account"
        def depositResponse = client.post(
                requestContentType: "application/json",
                path: "/account/$fromAccountNumber/deposit",
                body: depositRequestBody
        )

        then: "deposit response is OK"
        depositResponse.status == 200

        and: "money for account have been added"
        def accountResponse = getAccountResponse(fromAccountNumber)
        accountResponse.responseData.accountNumber == fromAccountNumber
        accountResponse.responseData.moneyDto.amount == 10.0

    }

    def "transfer money between accounts"() {
        given:
        def transferRequestBody = [amount: 10.0, currencyCode: "PLN"]

        when: "transfer money from account $fromAccountNumber to account $destinationAccountNumber"
        def transferResponse = client.post(
                requestContentType: "application/json",
                path: "/transfer/$fromAccountNumber/$destinationAccountNumber",
                body: transferRequestBody
        )

        then:
        transferResponse.status == 200
    }

    def "withdraw money from account"() {
        given:
        def withdrawRequestBody = [amount: 1.0, currencyCode: "PLN"]

        when: "withdraw money from account $destinationAccountNumber"
        def withdrawResponse = client.post(
                requestContentType: "application/json",
                path: "/account/$destinationAccountNumber/withdraw",
                body: withdrawRequestBody
        )

        then: "withdraw response is OK"
        withdrawResponse.status == 200

        then: "money from account have been withdraw"
        def accountResponse = getAccountResponse(destinationAccountNumber)
        accountResponse.responseData.accountNumber == destinationAccountNumber
        accountResponse.responseData.moneyDto.amount == 9.0
    }
}
