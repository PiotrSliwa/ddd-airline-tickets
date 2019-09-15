package tech.eversoft.airlines.ordering.transaction.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.ordering.order.domain.OrderRepository;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionCreated;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionRepository;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionService;

@Configuration
public class TransactionConfiguration {

    @Bean
    public TransactionService transactionService(
            DomainEventPublisher<TransactionCreated> publisher,
            OrderRepository orderRepository,
            TransactionRepository transactionRepository)
    {
        return new TransactionService(publisher, orderRepository, transactionRepository);
    }

}
