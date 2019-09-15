package tech.eversoft.airlines.ordering.order.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.common.domain.ClientId;
import tech.eversoft.airlines.common.domain.FlightId;
import tech.eversoft.airlines.ordering.order.domain.Order;
import tech.eversoft.airlines.ordering.order.domain.OrderService;

@RestController
@AllArgsConstructor
public class OrderCommandController {
    //TODO Remove "final" keywords from Spring components
    private final OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order create(@RequestBody CreateOrderCommand createOrderCommand) {
        return orderService.create(new ClientId(createOrderCommand.getClientId()), new FlightId(createOrderCommand.getFlightId()));
    }

}
