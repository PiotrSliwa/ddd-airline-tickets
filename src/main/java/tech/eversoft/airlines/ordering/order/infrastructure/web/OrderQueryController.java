package tech.eversoft.airlines.ordering.order.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.ordering.order.domain.Order;
import tech.eversoft.airlines.ordering.order.domain.OrderRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderQueryController {
    private OrderRepository orderRepository;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
