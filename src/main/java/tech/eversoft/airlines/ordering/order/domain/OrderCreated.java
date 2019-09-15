package tech.eversoft.airlines.ordering.order.domain;

import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.common.events.DomainEvent;

@Value
public class OrderCreated implements DomainEvent {
    @NonNull OrderId orderId;
}
