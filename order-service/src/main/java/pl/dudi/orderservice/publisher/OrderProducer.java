package pl.dudi.orderservice.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.dudi.orderservice.dto.OrderEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${rabbitmq.exchange.order.name}")
    private String exchange;
    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendEvent(OrderEvent event) {
        log.info(String.format("Order event sent to RabbitMQ --> %s",event));
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, event);
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, event);
    }
}
