package tech.eversoft.airlines.common.domain

import spock.lang.Specification
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.flight.domain.InvalidFlightIdFormat

class FlightIdTest extends Specification {
    def "Create"() {
        when:
        new FlightId(id)

        then:
        noExceptionThrown()

        where:
        id << ['XYZ123123', 'aaa999111']
    }

    def "InvalidId"() {
        when:
        new FlightId(id)

        then:
        thrown(InvalidFlightIdFormat)

        where:
        id << ['', 'a', '123123', 'bbb', 'abcd123123', 'aaa1231234']
    }
}
