package tech.eversoft.airlines.pricing.calculation.infrastructure.web;

import lombok.Value;
import tech.eversoft.airlines.pricing.calculation.domain.Calculation;
import tech.eversoft.airlines.pricing.discount.domain.Discount;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class CalculationDto {
    Long calculationId;
    Long transactionId;
    Long dollarPrice;
    List<String> discounts;

    public static CalculationDto of(Calculation calculation) {
        return new CalculationDto(
                calculation.getCalculationId().getId(),
                calculation.getTransactionId().getId(),
                calculation.getCalculatedPrice().getAmount(),
                convertDiscounts(calculation.getDiscounts()));
    }

    private static List<String> convertDiscounts(List<Discount> discounts) {
        return discounts.stream().map(Object::toString).collect(Collectors.toList());
    }
}
