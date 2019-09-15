package tech.eversoft.airlines.ordering.order.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.ClientId
import tech.eversoft.airlines.common.events.DomainEventPublisher
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.ordering.transaction.domain.*
import tech.eversoft.airlines.pricing.calculation.domain.CalculationId
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated

class TransactionServiceTest extends Specification {

    def clientId = new ClientId(11L)
    def flightId = new FlightId('XYZ123123')
    def orderId = new OrderId(42L)
    def order = new Order(orderId, clientId, flightId)
    def price = new DollarPrice(1000L)
    def transactionId = new TransactionId(42L)
    def calculationId = new CalculationId(9182L)

    def publisher = Mock(DomainEventPublisher)
    def orderRepository = Mock(OrderRepository)
    def transactionRepository = Mock(TransactionRepository)
    def transactionService = new TransactionService(publisher, orderRepository, transactionRepository)

    def "Handle OrderCreated"() {
        given:
        def savedTransaction = Mock(Transaction) {
            getTransactionId() >> transactionId
        }
        def orderCreated = new OrderCreated(orderId)

        when:
        transactionService.handle(orderCreated)

        then:
        1 * orderRepository.findById(orderId) >> Optional.of(order)
        1 * transactionRepository.save({ it.orderId == orderId }) >> savedTransaction
        1 * publisher.publish({ it -> it.transactionId == transactionId })
    }

    def "Handle PriceCalculated"() {
        given:
        def transaction = Mock(Transaction)
        def calculationId = new CalculationId(110L)
        def priceCalculated = new PriceCalculated(calculationId, transactionId, price)

        when:
        transactionService.handle(priceCalculated)

        then:
        1 * transactionRepository.findById(transactionId) >> Optional.of(transaction)
        1 * transaction.handle(priceCalculated)
        1 * transactionRepository.save(transaction)
    }

    def "Handle TransactionPaid"() {
        given:
        def transaction = new Transaction(transactionId, orderId, calculationId, price)
        def transactionPaid = new TransactionPaid(transactionId)

        when:
        transactionService.handle(transactionPaid)

        then:
        1 * transactionRepository.findById(transactionId) >> Optional.of(transaction)
        1 * orderRepository.findById(orderId) >> Optional.of(order)
        1 * orderRepository.save({ it.status == Order.Status.Confirmed })
    }

    def "Handle TransactionPaymentRejected"() {
        given:
        def transaction = new Transaction(transactionId, orderId, calculationId, price)
        def transactionPaymentRejected = new TransactionPaymentRejected(transactionId)

        when:
        transactionService.handle(transactionPaymentRejected)

        then:
        1 * transactionRepository.findById(transactionId) >> Optional.of(transaction)
        1 * orderRepository.findById(orderId) >> Optional.of(order)
        1 * orderRepository.save({ it.status == Order.Status.Rejected })
    }
}
