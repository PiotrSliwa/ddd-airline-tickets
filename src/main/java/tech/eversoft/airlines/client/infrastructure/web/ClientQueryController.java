package tech.eversoft.airlines.client.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.client.domain.ClientRepository;
import tech.eversoft.airlines.client.domain.ClientId;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class ClientQueryController {
    private ClientRepository clientRepository;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public Collection<Client> getAll() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public Client get(@PathVariable("id") Long id) {
        return clientRepository.findById(new ClientId(id)).orElseThrow();
    }
}
