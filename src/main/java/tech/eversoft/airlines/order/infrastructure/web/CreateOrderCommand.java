package tech.eversoft.airlines.order.infrastructure.web;

import lombok.Value;

@Value
public class CreateOrderCommand {
    Long clientId;
    String flightId;
}
