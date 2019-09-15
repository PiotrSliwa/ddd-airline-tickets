package tech.eversoft.airlines.ordering.transaction.domain;

import lombok.Value;
import tech.eversoft.airlines.common.events.DomainEvent;
import tech.eversoft.airlines.ordering.order.domain.OrderId;

@Value
public class TransactionCreated implements DomainEvent {
    OrderId orderId;
    TransactionId transactionId;
}
