package tech.eversoft.airlines.ordering.transaction.domain;

import lombok.AllArgsConstructor;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.ordering.order.domain.OrderCreated;
import tech.eversoft.airlines.ordering.order.domain.OrderRepository;
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated;

@AllArgsConstructor
public class TransactionService {
    private final DomainEventPublisher<TransactionCreated> publisher;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    public void handle(OrderCreated orderCreated) {
        var order = orderRepository.findById(orderCreated.getOrderId()).orElseThrow();
        var transaction = new Transaction(order.getId());
        var saved = transactionRepository.save(transaction);
        publisher.publish(new TransactionCreated(order.getId(), saved.getTransactionId()));
    }

    public void handle(PriceCalculated priceCalculated) {
        var transaction = transactionRepository.findById(priceCalculated.getTransactionId()).orElseThrow();
        transaction.handle(priceCalculated);
        transactionRepository.save(transaction);
    }

    public void handle(TransactionPaid transactionPaid) {
        var transaction = transactionRepository.findById(transactionPaid.getTransactionId()).orElseThrow();
        var order = orderRepository.findById(transaction.getOrderId()).orElseThrow();
        order.handle(transactionPaid);
        orderRepository.save(order);
    }

    public void handle(TransactionPaymentRejected transactionPaymentRejected) {
        var transaction = transactionRepository.findById(transactionPaymentRejected.getTransactionId()).orElseThrow();
        var order = orderRepository.findById(transaction.getOrderId()).orElseThrow();
        order.handle(transactionPaymentRejected);
        orderRepository.save(order);
    }

}
