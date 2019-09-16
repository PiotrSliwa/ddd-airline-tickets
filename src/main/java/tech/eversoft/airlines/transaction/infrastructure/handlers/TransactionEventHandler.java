package tech.eversoft.airlines.transaction.infrastructure.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tech.eversoft.airlines.common.events.DomainEvent;
import tech.eversoft.airlines.order.domain.OrderCreated;
import tech.eversoft.airlines.transaction.domain.TransactionPaid;
import tech.eversoft.airlines.transaction.domain.TransactionPaymentRejected;
import tech.eversoft.airlines.transaction.domain.TransactionService;
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionEventHandler {
    private TransactionService transactionService;

    @EventListener
    public void handle(OrderCreated orderCreated) {
        log(orderCreated);
        transactionService.handle(orderCreated);
    }

    @EventListener
    public void handle(PriceCalculated priceCalculated) {
        log(priceCalculated);
        transactionService.handle(priceCalculated);
    }

    @EventListener
    public void handle(TransactionPaid transactionPaid) {
        log(transactionPaid);
        transactionService.handle(transactionPaid);
    }

    @EventListener
    public void handle(TransactionPaymentRejected transactionPaymentRejected) {
        log(transactionPaymentRejected);
        transactionService.handle(transactionPaymentRejected);
    }

    //TODO Logging with an aspect
    private void log(DomainEvent event) {
        log.info(String.format("Handling domain event: %s", event));
    }
}
