package tech.eversoft.airlines.pricing.discount.domain;

import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.flight.domain.Flight;

public interface Discount {
    boolean isApplicable(Client client, Flight flight);
    DollarPrice apply(DollarPrice base);
}
