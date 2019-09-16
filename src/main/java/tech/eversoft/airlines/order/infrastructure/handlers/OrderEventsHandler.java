package tech.eversoft.airlines.order.infrastructure.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tech.eversoft.airlines.common.events.DomainEvent;
import tech.eversoft.airlines.order.domain.OrderService;
import tech.eversoft.airlines.transaction.domain.TransactionCreated;

@Service
@Slf4j
@AllArgsConstructor
public class OrderEventsHandler {
    private OrderService orderService;

    @EventListener
    public void handle(TransactionCreated transactionCreated) {
        log(transactionCreated);
        orderService.handle(transactionCreated);
    }

    //TODO Logging with an aspect
    private void log(DomainEvent event) {
        log.info(String.format("Handling domain event: %s", event));
    }
}
