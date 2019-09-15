package tech.eversoft.airlines.flight.application

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.Brand
import tech.eversoft.airlines.flight.domain.FlightRepository
import tech.eversoft.airlines.flight.infrastructure.web.CreateFlightCommand

class FlightCommandServiceTest extends Specification {
    def "Create"() {
        given:
        def flightRepository = Mock(FlightRepository)
        def service = new FlightCommandService(flightRepository)

        and:
        def command = new CreateFlightCommand('XYZ123123', 'wroclaw', 'cairo', 10, 20, 2, 100, Brand.A)

        when:
        def result = service.create(command)

        then:
        verifyAll(result) {
            flightId.id == command.flightId
            route.origin.name == command.origin
            route.destination.name == command.destination
            schedule.localTime.getHour() == command.timeHour
            schedule.localTime.getMinute() == command.timeMinute
            schedule.price.amount == command.dollarPrice
            brand == command.brand
        }

        and:
        1 * flightRepository.save({
            it.flightId.id == command.flightId
            it.route.origin.name == command.origin
            it.route.destination.name == command.destination
            it.schedule.localTime.getHour() == command.timeHour
            it.schedule.localTime.getMinute() == command.timeMinute
            it.schedule.price.amount == command.dollarPrice
            it.brand == command.brand
        })
    }
}
