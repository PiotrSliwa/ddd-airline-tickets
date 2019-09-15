package tech.eversoft.airlines.flight.infrastructure.persistence;

import lombok.Data;
import org.springframework.stereotype.Repository;
import tech.eversoft.airlines.common.domain.FlightId;
import tech.eversoft.airlines.flight.domain.Flight;
import tech.eversoft.airlines.flight.domain.FlightRepository;

import java.util.*;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
    private Map<String, Flight> map = new HashMap<>();

    @Override
    synchronized public Flight save(Flight flight) {
        map.put(flight.getFlightId().getId(), flight);
        return flight;
    }

    @Override
    synchronized public Optional<Flight> findById(FlightId id) {
        return Optional.ofNullable(map.get(id.getId()));
    }

    @Override
    synchronized public Collection<Flight> findAll() {
        return map.values();
    }
}
