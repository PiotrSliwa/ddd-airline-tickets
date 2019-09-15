package tech.eversoft.airlines.pricing.discount.domain

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.DollarPrice

class StandardDiscountCalculatorTest extends Specification {
    def "Calculate"() {
        given:
        def dollarPrice = new DollarPrice(price)
        def calculator = new StandardDiscountCalculator()

        expect:
        new DollarPrice(expected) == calculator.calculate(dollarPrice)

        where:
        price | expected
        10    | 10
        20    | 20
        23    | 20
        25    | 20
        26    | 21
        30    | 25
        40    | 35
    }
}
