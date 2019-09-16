package tech.eversoft.airlines.transaction.domain;

import lombok.Value;
import tech.eversoft.airlines.common.events.DomainEvent;
import tech.eversoft.airlines.order.domain.OrderId;

@Value
public class TransactionCreated implements DomainEvent {
    OrderId orderId;
    TransactionId transactionId;
}
