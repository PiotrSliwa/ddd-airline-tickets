package tech.eversoft.airlines.ordering.transaction.domain;

import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.common.events.DomainEvent;

@Value
public class TransactionPaymentRejected implements DomainEvent {
    @NonNull TransactionId transactionId;
}
