package tech.eversoft.airlines.pricing.discount.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.Client
import tech.eversoft.airlines.common.domain.Brand
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.flight.domain.Flight
import tech.eversoft.airlines.flight.domain.Route
import tech.eversoft.airlines.flight.domain.Schedule

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BirthdayDiscountTest extends Specification {

    def discountCalculator = Mock(DiscountCalculator)
    def birthdayDiscount = new BirthdayDiscount(discountCalculator)

    def "IsApplicable"() {
        given:
        def client = new Client(dateOfBirth)
        def schedule = Mock(Schedule)
        def flightTime = LocalTime.of(19, 13)
        def flight = Flight
                .builder()
                .flightId(Mock(FlightId))
                .route(Mock(Route))
                .schedule(schedule)
                .brand(Brand.A)
                .build()

        when:
        def result = birthdayDiscount.isApplicable(client, flight)

        then:
        1 * schedule.getDateTime(_) >> LocalDateTime.of(flightDate, flightTime)

        and:
        result == expected

        where:
        dateOfBirth                                 | flightDate      | expected
        LocalDate.now().minusYears(50)              | LocalDate.now() | true
        LocalDate.now().minusYears(50).minusDays(1) | LocalDate.now() | false
    }

    def "Apply"() {
        given:
        def oldPrice = Mock(DollarPrice)
        def newPrice = Mock(DollarPrice)

        when:
        def result = birthdayDiscount.apply(oldPrice)

        then:
        1 * discountCalculator.calculate(oldPrice) >> newPrice

        and:
        result == newPrice
    }
}
