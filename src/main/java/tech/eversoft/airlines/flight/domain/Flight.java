package tech.eversoft.airlines.flight.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import tech.eversoft.airlines.common.domain.Brand;

@Data
@Builder
public class Flight {
    @NonNull private final FlightId flightId;
    @NonNull private final Route route;
    @NonNull private final Schedule schedule;
    @NonNull private final Brand brand;
}
