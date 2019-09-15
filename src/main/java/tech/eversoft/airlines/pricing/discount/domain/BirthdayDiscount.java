package tech.eversoft.airlines.pricing.discount.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.flight.domain.Flight;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString(exclude = "discountCalculator")
public class BirthdayDiscount implements Discount {
    private final DiscountCalculator discountCalculator;

    @Override
    public boolean isApplicable(Client client, Flight flight) {
        var flightDateTime = flight.getSchedule().getDateTime(LocalDateTime.now());
        var birthDate = client.getDateOfBirth();
        return flightDateTime.getMonth() == birthDate.getMonth()
                && flightDateTime.getDayOfMonth() == birthDate.getDayOfMonth();
    }

    @Override
    public DollarPrice apply(DollarPrice base) {
        return discountCalculator.calculate(base);
    }
}
