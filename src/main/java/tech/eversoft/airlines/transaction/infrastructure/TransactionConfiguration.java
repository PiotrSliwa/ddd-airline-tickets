package tech.eversoft.airlines.transaction.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.order.domain.OrderRepository;
import tech.eversoft.airlines.transaction.domain.TransactionCreated;
import tech.eversoft.airlines.transaction.domain.TransactionRepository;
import tech.eversoft.airlines.transaction.domain.TransactionService;

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
