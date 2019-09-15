package tech.eversoft.airlines.flight.domain;

import java.util.Collection;
import java.util.Optional;

public interface FlightRepository {
    Flight save(Flight flight);
    Optional<Flight> findById(FlightId id);
    Collection<Flight> findAll();
}
