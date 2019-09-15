package tech.eversoft.airlines.flight.infrastructure.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ImportFlightCommand {
    @NonNull String url;
}
