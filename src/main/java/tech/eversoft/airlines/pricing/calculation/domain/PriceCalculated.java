package tech.eversoft.airlines.pricing.calculation.domain;

import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.common.events.DomainEvent;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionId;

@Value
public class PriceCalculated implements DomainEvent {
    @NonNull CalculationId calculationId;
    @NonNull TransactionId transactionId;
    @NonNull DollarPrice calculatedPrice;
}
