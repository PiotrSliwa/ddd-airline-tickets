package tech.eversoft.airlines.order.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.ClientId
import tech.eversoft.airlines.common.events.DomainEventPublisher
import tech.eversoft.airlines.flight.domain.FlightId

class OrderServiceTest extends Specification {
    def "Create"() {
        given:
        def clientId = new ClientId(42L)
        def flightId = new FlightId('XYZ123123')
        def orderId = new OrderId(78L)
        def savedOrder = Mock(Order) { getId() >> orderId }
        def publisher = Mock(DomainEventPublisher)
        def repository = Mock(OrderRepository)
        def service = new OrderService(publisher, repository)

        when:
        def result = service.create(clientId, flightId)

        then:
        1 * repository.save({ it.clientId == clientId && it.flightId == flightId }) >> savedOrder
        1 * publisher.publish({ it.orderId == orderId })

        and:
        result == savedOrder
    }
}
