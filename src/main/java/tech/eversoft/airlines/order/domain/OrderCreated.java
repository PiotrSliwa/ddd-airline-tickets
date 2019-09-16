package tech.eversoft.airlines.order.domain;

import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.common.events.DomainEvent;

@Value
public class OrderCreated implements DomainEvent {
    @NonNull OrderId orderId;
}
