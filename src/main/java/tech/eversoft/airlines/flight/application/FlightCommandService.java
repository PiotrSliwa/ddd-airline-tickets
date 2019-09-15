package tech.eversoft.airlines.flight.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.eversoft.airlines.common.domain.DollarPrice;
import tech.eversoft.airlines.flight.domain.FlightId;
import tech.eversoft.airlines.flight.domain.*;
import tech.eversoft.airlines.flight.infrastructure.web.CreateFlightCommand;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class FlightCommandService {
    private FlightRepository flightRepository;

    public Flight create(CreateFlightCommand command) {
        var flight = Flight.builder()
                .flightId(new FlightId(command.getFlightId()))
                .route(new Route(new Location(command.getOrigin()), new Location(command.getDestination())))
                .schedule(new Schedule(
                        LocalTime.of(command.getTimeHour(), command.getTimeMinute()),
                        DayOfWeek.of(command.getDayOfWeek()),
                        new DollarPrice(command.getDollarPrice())))
                .brand(command.getBrand())
                .build();
        flightRepository.save(flight);
        return flight;
    }
}
