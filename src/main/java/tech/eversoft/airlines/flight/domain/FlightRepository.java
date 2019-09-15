package tech.eversoft.airlines.flight.domain;

import tech.eversoft.airlines.common.domain.FlightId;

import java.util.Collection;
import java.util.Optional;

public interface FlightRepository {
    Flight save(Flight flight);
    Optional<Flight> findById(FlightId id);
    Collection<Flight> findAll();
}
