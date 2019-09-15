package tech.eversoft.airlines.pricing.discount.domain;

import tech.eversoft.airlines.common.domain.DollarPrice;

public interface DiscountCalculator {
    DollarPrice calculate(DollarPrice price);
}
