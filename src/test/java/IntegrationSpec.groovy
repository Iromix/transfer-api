import groovyx.net.http.RESTClient
import spock.lang.Specification

class IntegrationSpec extends Specification {

    RESTClient client = new RESTClient('http://localhost:7000')

    def setupSpec() {
        ApplicationStart.startServer()
    }

    def cleanupSpec() {
        ApplicationStart.stopServer()
    }
}
