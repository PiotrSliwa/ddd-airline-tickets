package tech.eversoft.airlines.common.events;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
