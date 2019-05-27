package transfer

import groovyx.net.http.RESTClient
import spock.lang.Specification

import static transfer.ApplicationStart.*

class IntegrationSpec extends Specification {

    RESTClient client = new RESTClient('http://localhost:7000')

    def setupSpec() {
        startServer()
    }

    def cleanupSpec() {
        stopServer()
    }
}
