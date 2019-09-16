package tech.eversoft.airlines.pricing.calculation.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.common.domain.Brand;
import tech.eversoft.airlines.flight.domain.Flight;
import tech.eversoft.airlines.transaction.domain.TransactionId;
import tech.eversoft.airlines.pricing.discount.domain.DiscountFactory;

class NotSupportedBrand extends RuntimeException {
    NotSupportedBrand(Brand brand) {
        super(String.format("Brand '%s' is not yet supported!", brand.name()));
    }
}

@AllArgsConstructor
@NoArgsConstructor
public class CalculationFactory {
    private DiscountFactory discountFactory;

    public Calculation create(TransactionId transactionId, Client client, Flight flight) {
        var discounts = discountFactory.create(client, flight);
        var basePrice = flight.getSchedule().getPrice();

        //TODO Extract to a repository mapping brand names with proper strategies - then, remove the Brand enum as well
        switch (flight.getBrand()) {
            case A:
                return Calculation.withSavedDiscounts(transactionId, discounts, basePrice);
            case B:
                return Calculation.withoutSavedDiscounts(transactionId, discounts, basePrice);
            default:
                throw new NotSupportedBrand(flight.getBrand());
        }
    }

}
