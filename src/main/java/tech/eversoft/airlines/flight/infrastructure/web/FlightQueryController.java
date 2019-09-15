package tech.eversoft.airlines.flight.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.flight.domain.Flight;
import tech.eversoft.airlines.flight.domain.FlightRepository;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class FlightQueryController {
    private final FlightRepository flightRepository;

    @RequestMapping(value = "/flight", method = RequestMethod.GET)
    public Collection<Flight> get() {
        return flightRepository.findAll();
    }
}
