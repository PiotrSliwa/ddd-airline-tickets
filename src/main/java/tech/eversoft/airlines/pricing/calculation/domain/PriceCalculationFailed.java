package tech.eversoft.airlines.pricing.calculation.domain;

import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.transaction.domain.TransactionId;

@Value
public class PriceCalculationFailed {
    @NonNull CalculationId calculationId;
    @NonNull TransactionId transactionId;
    @NonNull String reason;
}
