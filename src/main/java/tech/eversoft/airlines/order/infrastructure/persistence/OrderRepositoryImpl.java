package tech.eversoft.airlines.order.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import tech.eversoft.airlines.order.domain.Order;
import tech.eversoft.airlines.order.domain.OrderId;
import tech.eversoft.airlines.order.domain.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private List<Order> list = new ArrayList<>();

    @Override
    synchronized public Order save(Order order) {
        if (order.getId() != null) {
            list.set(order.getId().getId().intValue(), order);
            return order;
        }
        var id = new OrderId((long) list.size());
        list.add(order);
        order.setId(id);
        return order;
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        if (id.getId() >= list.size()) {
            return Optional.empty();
        }
        return Optional.of(list.get(id.getId().intValue()));
    }

    @Override
    public List<Order> findAll() {
        return list;
    }
}
