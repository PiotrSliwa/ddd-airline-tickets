package tech.eversoft.airlines.pricing.calculation.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.airlines.client.domain.ClientRepository;
import tech.eversoft.airlines.common.events.DomainEventPublisher;
import tech.eversoft.airlines.flight.domain.Continent;
import tech.eversoft.airlines.flight.domain.FlightRepository;
import tech.eversoft.airlines.order.domain.OrderRepository;
import tech.eversoft.airlines.transaction.domain.TransactionRepository;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationFactory;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationRepository;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationService;
import tech.eversoft.airlines.pricing.calculation.domain.PriceCalculated;
import tech.eversoft.airlines.pricing.discount.domain.*;

import java.time.DayOfWeek;
import java.util.List;

@Configuration
@AllArgsConstructor
public class CalculationConfiguration {

    @Bean
    public DiscountCalculator standardDiscountCalculator() {
        return new StandardDiscountCalculator();
    }

    @Bean
    public DiscountFactory discountFactory(DiscountCalculator discountCalculator) {
        return new DiscountFactory(List.of(
                new BirthdayDiscount(discountCalculator),
                new FlightParameterDiscount(discountCalculator, Continent.Africa, DayOfWeek.THURSDAY)));
        //TODO Create a CRUD REST API for managing FlightParameterDiscounts
    }

    @Bean
    public CalculationFactory calculationFactory(DiscountFactory discountFactory) {
        return new CalculationFactory(discountFactory);
    }

    @Bean
    public CalculationService calculationService(DomainEventPublisher<PriceCalculated> publisher,
                                                 TransactionRepository transactionRepository,
                                                 OrderRepository orderRepository,
                                                 ClientRepository clientRepository,
                                                 FlightRepository flightRepository,
                                                 CalculationFactory calculationFactory,
                                                 CalculationRepository calculationRepository)
    {
        return new CalculationService(
                publisher,
                transactionRepository,
                orderRepository,
                clientRepository,
                flightRepository,
                calculationFactory,
                calculationRepository);
    }

}
