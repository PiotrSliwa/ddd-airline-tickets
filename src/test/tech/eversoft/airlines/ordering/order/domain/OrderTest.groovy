package tech.eversoft.airlines.ordering.order.domain

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.ClientId
import tech.eversoft.airlines.common.domain.FlightId
import tech.eversoft.airlines.ordering.transaction.domain.TransactionId
import tech.eversoft.airlines.ordering.transaction.domain.TransactionPaid
import tech.eversoft.airlines.ordering.transaction.domain.TransactionPaymentRejected

class OrderTest extends Specification {
    def transactionId = new TransactionId(42L)
    def clientId = new ClientId(42L)
    def flightId = new FlightId('XYZ123123')
    def order = new Order(clientId, flightId)

    def "Init"() {
        expect:
        order.getStatus() == Order.Status.Created
    }

    def "Confirm"() {
        when:
        def transactionPaid = new TransactionPaid(transactionId)
        order.handle(transactionPaid)

        then:
        order.getStatus() == Order.Status.Confirmed
    }

    def "Reject"() {
        when:
        def transactionPaymentRejected = new TransactionPaymentRejected(transactionId)
        order.handle(transactionPaymentRejected)

        then:
        order.getStatus() == Order.Status.Rejected
    }
}
