package tech.eversoft.airlines.flight.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tech.eversoft.airlines.flight.application.FlightCommandService;
import tech.eversoft.airlines.flight.domain.Flight;

@RestController
@AllArgsConstructor
@Slf4j
public class FlightCommandController {
    private FlightCommandService flightCommandService;

    @RequestMapping(value = "/flight", method = RequestMethod.POST)
    public Flight create(@RequestBody CreateFlightCommand command) {
        return flightCommandService.create(command);
    }

    @RequestMapping(value = "/flight/import", method = RequestMethod.POST)
    @SneakyThrows
    public Flight importFlight(@RequestBody ImportFlightCommand importCommand) {
        var restTemplate = new RestTemplate();
        var objectMapper = new ObjectMapper();
        var response = restTemplate.getForObject(importCommand.getUrl(), String.class);
        var createCommand = objectMapper.readValue(response, CreateFlightCommand.class);
        return flightCommandService.create(createCommand);
        //TODO Error handling!
    }

}
