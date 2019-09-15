package tech.eversoft.airlines.client.domain;

import tech.eversoft.airlines.common.domain.ClientId;

import java.util.Collection;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(ClientId id);
    Collection<Client> findAll();
}
