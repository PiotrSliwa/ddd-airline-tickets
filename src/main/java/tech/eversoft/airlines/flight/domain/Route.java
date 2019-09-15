package tech.eversoft.airlines.flight.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
public class Route {
    @NonNull private final Location origin;
    @NonNull private final Location destination;
}
