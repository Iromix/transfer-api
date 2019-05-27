package transfer

import spock.lang.Stepwise

@Stepwise
class ApiSpec extends IntegrationSpec {

    def "create accounts for transfer"() {
        when: "create first account"
        def response = client.post(
                requestContentType: "application/json",
                path: "/account/currency/PLN"
        )
        then:
        response.status == 201

        when: "create second account"
        def secondResponse = client.post(
                requestContentType: "application/json",
                path: "/account/currency/PLN"
        )
        then:
        secondResponse.status == 201
    }

    def "deposit money on account before transfer"() {
        given:
        def depositRequestBody = [amount: 10.0, currencyCode: "PLN"]

        when: "deposit money on first account"
        def depositRresponse = client.post(
                requestContentType: "application/json",
                path: "/account/1/deposit",
                body: depositRequestBody
        )

        then: "deposit response is OK"
        depositRresponse.status == 201

        then: "money for account have been added"
        def accountResponse = getAccountResponse(1)
        accountResponse.status == 200
        accountResponse.responseData.accountNumber == 1
        accountResponse.responseData.moneyDto.amount == 10.0

    }

    def "transfer money between accounts"() {
        given:
        def transferRequestBody = [amount: 10.0, currencyCode: "PLN"]

        when: "transfer money from account 1 to account 2"
        def transferResponse = client.post(
                requestContentType: "application/json",
                path: "/transfer/1/2",
                body: transferRequestBody
        )

        then:
        transferResponse.status == 201
    }

    def "withdraw money from account"() {
        given:
        def withdrawRequestBody = [amount: 1.0, currencyCode: "PLN"]

        when: "withdraw money from account 2"
        def withdrawResponse = client.post(
                requestContentType: "application/json",
                path: "/account/2/withdraw",
                body: withdrawRequestBody
        )

        then: "withdraw response is OK"
        withdrawResponse.status == 201

        then: "money from account have been withdraw"
        def accountResponse = getAccountResponse(2)
        accountResponse.status == 200
        accountResponse.responseData.accountNumber == 2
        accountResponse.responseData.moneyDto.amount == 9.0
    }

    def getAccountResponse(Integer accountNumber) {
        return client.get(
                requestContentType: "application/json",
                path: "/account/" + accountNumber
        )
    }
}
