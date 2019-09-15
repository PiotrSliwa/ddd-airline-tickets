package tech.eversoft.airlines.client.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.client.domain.ClientRepository;

@RestController
@AllArgsConstructor
public class ClientCommandController {
    private final ClientRepository clientRepository;

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public Client create(@RequestBody CreateClientCommand createClientCommand) {
        return clientRepository.save(new Client(createClientCommand.getBirthDate()));
    }
}
