package tech.eversoft.airlines.common.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@Slf4j
public class SimpleDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent domainEvent) {
        log.info(String.format("Published event: %s", domainEvent));
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
