package tech.eversoft.airlines.common.domain;

import lombok.Data;
import lombok.NonNull;

class InvalidFlightIdFormat extends RuntimeException {
    public InvalidFlightIdFormat(String id) {
        super(String.format("Invalid FlightId: %s", id));
    }
}

@Data
public class FlightId {
    @NonNull private final String id;

    public FlightId(@NonNull String id) {
        if (!id.matches("\\w{3}\\d{6}")) {
            throw new InvalidFlightIdFormat(id);
        }
        this.id = id;
    }
}
