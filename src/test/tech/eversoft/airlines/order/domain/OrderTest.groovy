package tech.eversoft.airlines.order.domain

import spock.lang.Specification
import tech.eversoft.airlines.client.domain.ClientId
import tech.eversoft.airlines.flight.domain.FlightId
import tech.eversoft.airlines.transaction.domain.TransactionId
import tech.eversoft.airlines.transaction.domain.TransactionPaid
import tech.eversoft.airlines.transaction.domain.TransactionPaymentRejected

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
