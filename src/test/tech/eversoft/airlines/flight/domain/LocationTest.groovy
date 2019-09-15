package tech.eversoft.airlines.flight.domain

import spock.lang.Specification

class LocationTest extends Specification {
    def "GetContinent"() {
        expect:
        continent == new Location(name).getContinent()

        where:
        name      | continent
        "Wroclaw" | Continent.Europe
        "wroclaw" | Continent.Europe
        "cairo"   | Continent.Africa
    }

    def "throw when unknown location given"() {
        when:
        new Location("unknown")

        then:
        thrown(UnknownLocationName)
    }
}
