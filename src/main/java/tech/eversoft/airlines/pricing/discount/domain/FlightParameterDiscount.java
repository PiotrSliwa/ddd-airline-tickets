package tech.eversoft.airlines.pricing.discount.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.flight.domain.Continent;
import tech.eversoft.airlines.flight.domain.Flight;

import java.time.DayOfWeek;

@AllArgsConstructor
@ToString(exclude = "discountCalculator")
public class FlightParameterDiscount implements Discount {
    private final DiscountCalculator discountCalculator;
    private final Continent destinationContinent;
    private final DayOfWeek dayOfWeek;

    @Override
    public boolean isApplicable(Client client, Flight flight) {
        return flight.getSchedule().getDayOfWeek().equals(dayOfWeek)
                && flight.getRoute().getDestination().getContinent().equals(destinationContinent);
    }

    @Override
    public DollarPrice apply(DollarPrice base) {
        return discountCalculator.calculate(base);
    }
}
