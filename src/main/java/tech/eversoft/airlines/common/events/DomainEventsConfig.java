package tech.eversoft.airlines.common.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventsConfig {

    @Bean
    DomainEventPublisher domainEvents(ApplicationEventPublisher applicationEventPublisher) {
        return new SimpleDomainEventPublisher(applicationEventPublisher);
    }
}
