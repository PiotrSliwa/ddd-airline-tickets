package tech.eversoft.airlines.pricing.discount.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.Client
import tech.eversoft.airlines.common.domain.Brand
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.flight.domain.Continent
import tech.eversoft.airlines.flight.domain.Flight
import tech.eversoft.airlines.flight.domain.Location
import tech.eversoft.airlines.flight.domain.Route
import tech.eversoft.airlines.flight.domain.Schedule

import java.time.DayOfWeek
import java.time.LocalTime

class FlightParameterDiscountTest extends Specification {
    def discountCalculator = Mock(DiscountCalculator)
    static def discountedContinent = Continent.Africa
    static def discountedDayOfWeek = DayOfWeek.THURSDAY
    def flightParameterDiscount = new FlightParameterDiscount(discountCalculator, discountedContinent, discountedDayOfWeek)

    def "IsApplicable"() {
        given:
        def client = Mock(Client)
        def schedule = new Schedule(LocalTime.of(15, 20), flightDayOfWeek, new DollarPrice(100L))
        def destination = Mock(Location) { getContinent() >> destinationContinent }
        def flight = Flight
                .builder()
                .flightId(Mock(FlightId))
                .route(new Route(Mock(Location), destination))
                .schedule(schedule)
                .brand(Brand.A)
                .build()

        when:
        def result = flightParameterDiscount.isApplicable(client, flight)

        then:
        result == expected

        where:
        flightDayOfWeek              | destinationContinent   | expected
        discountedDayOfWeek          | discountedContinent    | true
        discountedDayOfWeek          | Continent.NorthAmerica | false
        discountedDayOfWeek.minus(1) | discountedContinent    | false
    }

    def "Apply"() {
        given:
        def oldPrice = Mock(DollarPrice)
        def newPrice = Mock(DollarPrice)

        when:
        def result = flightParameterDiscount.apply(oldPrice)

        then:
        1 * discountCalculator.calculate(oldPrice) >> newPrice

        and:
        result == newPrice
    }
}
