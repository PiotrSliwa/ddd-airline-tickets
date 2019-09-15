package tech.eversoft.airlines.client.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import tech.eversoft.airlines.client.domain.Client;
import tech.eversoft.airlines.client.domain.ClientRepository;
import tech.eversoft.airlines.client.domain.ClientId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private List<Client> list = new ArrayList<>();

    @Override
    synchronized public Client save(Client client) {
        var id = new ClientId((long) list.size());
        list.add(client);
        client.setClientId(id);
        return client;
    }

    @Override
    synchronized public Optional<Client> findById(ClientId id) {
        if (id.getId() >= list.size()) {
            return Optional.empty();
        }
        return Optional.of(list.get(id.getId().intValue()));
    }

    @Override
    synchronized public Collection<Client> findAll() {
        return list;
    }
}
