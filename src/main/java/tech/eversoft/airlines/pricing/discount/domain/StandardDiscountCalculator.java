package tech.eversoft.airlines.pricing.discount.domain;

import tech.eversoft.airlines.common.domain.DollarPrice;

public class StandardDiscountCalculator implements DiscountCalculator {

    @Override
    public DollarPrice calculate(DollarPrice base) {
        long amount = calculate(base.getAmount());
        return new DollarPrice(amount);
    }

    private static long calculate(long amount) {
        if (amount < 20) {
            return amount;
        }
        if (amount < 25) {
            return 20;
        }
        return amount - 5;
    }

}
