package pl.dudi.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dudi.orderservice.dto.Order;
import pl.dudi.orderservice.dto.OrderEvent;
import pl.dudi.orderservice.publisher.OrderProducer;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping("/orders")
    public String makeAnOrder(
        @RequestBody Order order
    ) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order is in pending status");
        orderEvent.setOrder(order);
        orderProducer.sendEvent(orderEvent);
        return "Order sent to RabbitMQ";
    }
}
