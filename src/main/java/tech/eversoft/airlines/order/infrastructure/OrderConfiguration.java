package tech.eversoft.airlines.order.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.order.domain.OrderRepository;
import tech.eversoft.airlines.order.domain.OrderService;

@Configuration
@AllArgsConstructor
public class OrderConfiguration {
    private final DomainEventPublisher domainEventPublisher;

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderService(domainEventPublisher, orderRepository);
    }

}
