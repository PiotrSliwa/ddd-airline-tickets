package tech.eversoft.airlines.pricing.calculation.domain

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.DollarPrice
import tech.eversoft.airlines.ordering.transaction.domain.TransactionId
import tech.eversoft.airlines.pricing.discount.domain.Discount

class CalculationTest extends Specification {
    def "withSavedDiscounts"() {
        given:
        def basePrice = new DollarPrice(1000L)
        def transactionId = new TransactionId(71L)
        def discountA = Mock(Discount)
        def discountB = Mock(Discount)

        and:
        def discountedPriceA = new DollarPrice(32)
        def discountedPriceB = new DollarPrice(22)

        when:
        def calculation = Calculation.withSavedDiscounts(transactionId,  [discountA, discountB], basePrice)

        then:
        1 * discountA.apply(basePrice) >> discountedPriceA
        1 * discountB.apply(discountedPriceA) >> discountedPriceB

        and:
        calculation.calculatedPrice == discountedPriceB
    }
}
