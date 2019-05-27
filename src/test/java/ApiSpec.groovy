import groovyx.net.http.RESTClient
import spock.lang.Specification

class ApiSpec extends Specification {

    //todo make integration spec
    RESTClient client = new RESTClient('http://localhost:7000')

    def setupSpec() {
        ApplicationStart.startServer()
    }

    def "create accounts"() {
        given:
        def depositRequestBody = [amount: 10.0, currencyCode: "PLN"]
        def transferRequestBody = depositRequestBody

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

        when: "deposit money on first account"
        def depositRresponse = client.post(
                requestContentType: "application/json",
                path: "/account/1/deposit",
                body: depositRequestBody
        )

        then:
        depositRresponse.status == 201

        when: "get account"
        def accountResponse = client.get(
                requestContentType: "application/json",
                path: "/account/1"
        )
        then:
        accountResponse.status == 200
        accountResponse.responseData.accountNumber == 1
        accountResponse.responseData.moneyDto.amount == 10.0

        when: "transfer money from account 1 to account 2"
        def transferReponse = client.post(
                requestContentType: "application/json",
                path: "/transfer/1/2",
                body: transferRequestBody
        )

        then:
        transferReponse.status == 201
    }

    def cleanupSpec() {
        ApplicationStart.stopServer()
    }
}
