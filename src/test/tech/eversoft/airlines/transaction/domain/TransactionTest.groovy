package tech.eversoft.airlines.transaction.domain

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.order.domain.OrderId
import tech.eversoft.airlines.pricing.calculation.domain.CalculationId
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated

class TransactionTest extends Specification {
    def "Handle PriceCalculated"() {
        given:
        def orderId = new OrderId(42L)
        def calculationId = new CalculationId(22L)
        def transactionId = new TransactionId(99L)
        def price = new DollarPrice(10)
        def priceCalculated = new PriceCalculated(calculationId, transactionId, price)
        def transaction = new Transaction(orderId)

        when:
        transaction.handle(priceCalculated)

        then:
        transaction.price == price
    }
}
