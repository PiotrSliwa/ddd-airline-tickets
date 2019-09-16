package tech.eversoft.airlines.pricing.calculation.domain;

import lombok.*;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.transaction.domain.TransactionId;
import tech.eversoft.airlines.pricing.discount.domain.Discount;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Calculation {
    @Setter private CalculationId calculationId;
    @NonNull private final TransactionId transactionId;
    @NonNull private final List<Discount> discounts;
    private DollarPrice calculatedPrice;

    public static Calculation withSavedDiscounts(@NonNull TransactionId transactionId, @NonNull List<Discount> discounts, DollarPrice basePrice) {
        var calculatedPrice = calculate(basePrice, discounts);
        return new Calculation(transactionId, discounts, calculatedPrice);
    }

    public static Calculation withoutSavedDiscounts(@NonNull TransactionId transactionId, @NonNull List<Discount> discounts, DollarPrice basePrice) {
        var calculatedPrice = calculate(basePrice, discounts);
        return new Calculation(transactionId, Collections.emptyList(), calculatedPrice);
    }

    private Calculation(@NonNull TransactionId transactionId, @NonNull List<Discount> discounts, DollarPrice calculatedPrice) {
        this.transactionId = transactionId;
        this.discounts = discounts;
        this.calculatedPrice = calculatedPrice;
    }

    private static DollarPrice calculate(DollarPrice basePrice, List<Discount> discounts) {
        var price = basePrice;
        for (var discount : discounts) {
            price = discount.apply(price);
        }
        return price;
    }
}
