package tech.eversoft.airlines.pricing.calculation.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.Client
import tech.eversoft.airlines.client.domain.ClientRepository
import tech.eversoft.airlines.client.domain.ClientId
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.common.events.DomainEventPublisher
import tech.eversoft.airlines.flight.domain.Flight
import tech.eversoft.airlines.flight.domain.FlightRepository
import tech.eversoft.airlines.order.domain.Order
import tech.eversoft.airlines.order.domain.OrderId
import tech.eversoft.airlines.order.domain.OrderRepository
import tech.eversoft.airlines.transaction.domain.Transaction
import tech.eversoft.airlines.transaction.domain.TransactionCreated
import tech.eversoft.airlines.transaction.domain.TransactionId
import tech.eversoft.airlines.transaction.domain.TransactionRepository

class CalculationServiceTest extends Specification {
    def "Handle TransactionCreated"() {
        given:
        def publisher = Mock(DomainEventPublisher)
        def transactionRepository = Mock(TransactionRepository)
        def orderRepository = Mock(OrderRepository)
        def clientRepository = Mock(ClientRepository)
        def flightRepository = Mock(FlightRepository)
        def calculationFactory = Mock(CalculationFactory)
        def calculationRepository = Mock(CalculationRepository)
        def calculationService = new CalculationService(
                publisher: publisher,
                transactionRepository: transactionRepository,
                orderRepository: orderRepository,
                clientRepository: clientRepository,
                flightRepository: flightRepository,
                calculationFactory: calculationFactory,
                calculationRepository: calculationRepository)

        and:
        def transactionId = new TransactionId(21L)
        def orderId = new OrderId(11L)
        def transactionCreated = new TransactionCreated(orderId, transactionId)

        and:
        def clientId = new ClientId(34L)
        def flightId = new FlightId("XYZ123123")
        def order = new Order(orderId, clientId, flightId)
        def transaction = Mock(Transaction) {
            getOrderId() >> orderId
            getTransactionId() >> transactionId
        }
        def client = Mock(Client)
        def flight = Mock(Flight)
        def calculationId = new CalculationId(10944L)
        def calculatedPrice = new DollarPrice(506L)
        def calculation = Mock(Calculation) {
            getCalculationId() >> calculationId
            getCalculatedPrice() >> calculatedPrice
        }

        when:
        calculationService.handle(transactionCreated)

        then:
        1 * transactionRepository.findById(transactionId) >> Optional.of(transaction)
        1 * orderRepository.findById(orderId) >> Optional.of(order)
        1 * flightRepository.findById(flightId) >> Optional.of(flight)
        1 * clientRepository.findById(clientId) >> Optional.of(client)
        1 * calculationFactory.create(transactionId, client, flight) >> calculation
        1 * calculationRepository.save(calculation)
        1 * publisher.publish({ it.calculationId == calculationId && it.transactionId == transactionId && it.calculatedPrice == calculatedPrice })
    }
}
