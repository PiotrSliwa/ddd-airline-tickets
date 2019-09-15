package tech.eversoft.airlines.flight.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

class UnknownLocationName extends RuntimeException {
    public UnknownLocationName(String name) {
        super(String.format("Unknown location name: %s", name));
    }
}

@Getter
public class Location {
    @NonNull String name;
    @NonNull Continent continent;

    public Location(@NonNull String name) {
        this.name = name;
        this.continent = getContinent(name);
    }

    //TODO: Move to a LocationRepository and create locations via a dedicated LocationFactory
    private static final Map<String, Continent> locationContinent = Map.of(
            "wroclaw", Continent.Europe,
            "cairo", Continent.Africa
    );

    private static Continent getContinent(String name) {
        var continent = locationContinent.get(name.toLowerCase());
        if (continent == null) {
            throw new UnknownLocationName(name);
        }
        return continent;
    }
}
