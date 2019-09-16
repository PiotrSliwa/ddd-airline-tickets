package tech.eversoft.airlines.pricing.calculation.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tech.eversoft.airlines.client.domain.ClientRepository;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.flight.domain.FlightRepository;
import tech.eversoft.airlines.order.domain.OrderRepository;
import tech.eversoft.airlines.transaction.domain.TransactionCreated;
import tech.eversoft.airlines.transaction.domain.TransactionRepository;

@AllArgsConstructor
@NoArgsConstructor
public class CalculationService {
    private DomainEventPublisher<PriceCalculated> publisher;

    //TODO Microservices: Replace the direct calls to domain repositories with connection to ports (REST APIs)
    private TransactionRepository transactionRepository;
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private FlightRepository flightRepository;
    private CalculationFactory calculationFactory;
    private CalculationRepository calculationRepository;

    public void handle(TransactionCreated transactionCreated) {
        //TODO More verbose messages than the generic RuntimeException thrown by orElseThrown() below
        var transaction = transactionRepository.findById(transactionCreated.getTransactionId()).orElseThrow();
        var order = orderRepository.findById(transaction.getOrderId()).orElseThrow();
        var client = clientRepository.findById(order.getClientId()).orElseThrow();
        var flight = flightRepository.findById(order.getFlightId()).orElseThrow();
        var calculation = calculationFactory.create(transactionCreated.getTransactionId(), client, flight);
        calculationRepository.save(calculation);
        publisher.publish(new PriceCalculated(
                calculation.getCalculationId(),
                transactionCreated.getTransactionId(),
                calculation.getCalculatedPrice()));
    }
}
