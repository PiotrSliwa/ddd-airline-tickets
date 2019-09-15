package tech.eversoft.airlines.flight.infrastructure.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import tech.eversoft.airlines.common.domain.Brand;

@Data
@NoArgsConstructor
public class CreateFlightCommand {
    @NonNull String flightId;
    @NonNull String origin;
    @NonNull String destination;
    @NonNull Integer timeHour;
    @NonNull Integer timeMinute;
    @NonNull Integer dayOfWeek;
    @NonNull Long dollarPrice;
    @NonNull Brand brand;
}
