package tech.eversoft.airlines.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.order.domain.OrderId;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationId;
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    private TransactionId transactionId;
    @NonNull private final OrderId orderId;
    private CalculationId calculationId;
    private DollarPrice price;

    public void handle(PriceCalculated priceCalculated) {
        calculationId = priceCalculated.getCalculationId();
        price = priceCalculated.getCalculatedPrice();
    }
}
