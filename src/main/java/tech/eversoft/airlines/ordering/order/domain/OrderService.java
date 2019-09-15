package tech.eversoft.airlines.ordering.order.domain;

import lombok.AllArgsConstructor;
import tech.eversoft.airlines.common.domain.ClientId;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.common.domain.FlightId;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionCreated;

@AllArgsConstructor
public class OrderService {
    private final DomainEventPublisher<OrderCreated> publisher;
    private final OrderRepository repository;

    public Order create(ClientId clientId, FlightId flightId) {
        var order = new Order(clientId, flightId);
        var saved = repository.save(order);
        publisher.publish(new OrderCreated(saved.getId()));
        return saved;
    }

    public void handle(TransactionCreated transactionCreated) {
        var order = repository.findById(transactionCreated.getOrderId()).orElseThrow();
        order.handle(transactionCreated);
        repository.save(order);
    }

}
