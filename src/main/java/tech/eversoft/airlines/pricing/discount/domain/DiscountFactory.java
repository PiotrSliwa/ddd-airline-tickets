package tech.eversoft.airlines.pricing.discount.domain;

import lombok.AllArgsConstructor;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.flight.domain.Flight;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DiscountFactory {
    private List<Discount> discounts;

    public List<Discount> create(Client client, Flight flight) {
        return discounts
                .stream()
                .filter(discount -> discount.isApplicable(client, flight))
                .collect(Collectors.toList());
    }
}
