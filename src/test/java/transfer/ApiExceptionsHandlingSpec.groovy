package transfer

import groovyx.net.http.HttpResponseException
import spock.lang.Shared

class ApiExceptionsHandlingSpec extends IntegrationSpec {

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

    def "handle transfer exception"() {
        given:
        def transferRequestBody = [amount: 10.0, currencyCode: "PLN"]

        when: "try to transfer not existing money on account"
        client.post(
                requestContentType: "application/json",
                path: "/transfer/$fromAccountNumber/$destinationAccountNumber",
                body: transferRequestBody
        )

        then:
        def e = thrown(HttpResponseException)
        e.response.status == 400
    }

    def "handle query for not existing account"() {
        given:
        Integer notExistingAccountNumber = 0

        when: "try to fetch not existing account"
        getAccountResponse(notExistingAccountNumber)

        then:
        def e = thrown(HttpResponseException)
        e.response.status == 404
    }

    def "handle currencies mismatch exception"() {
        given:
        def differentCurrency = "GBP"
        def depositRequestBody = [amount: 10.0, currencyCode: differentCurrency]

        when: "try to deposit $differentCurrency currency on account with PLN currency"
        client.post(
                requestContentType: "application/json",
                path: "/account/$fromAccountNumber/deposit",
                body: depositRequestBody
        )

        then:
        def e = thrown(HttpResponseException)
        e.response.status == 500
    }
}
