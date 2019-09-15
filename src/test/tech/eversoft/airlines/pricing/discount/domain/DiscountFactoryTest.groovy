package tech.eversoft.airlines.pricing.discount.domain


import spock.lang.Specification
import tech.eversoft.airlines.client.domain.Client
import tech.eversoft.airlines.flight.domain.Flight

class DiscountFactoryTest extends Specification {
    def "Create"() {
        given:
        def client = Mock(Client)
        def flight = Mock(Flight)
        def discountA = Mock(Discount)
        def discountB = Mock(Discount)
        def discountC = Mock(Discount)
        def factory = new DiscountFactory([discountA, discountB, discountC])

        when:
        def result = factory.create(client, flight)

        then:
        1 * discountA.isApplicable(client, flight) >> true
        1 * discountB.isApplicable(client, flight) >> false
        1 * discountC.isApplicable(client, flight) >> true
        result == [discountA, discountC]
    }
}
