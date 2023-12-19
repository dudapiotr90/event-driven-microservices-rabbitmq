package pl.dudi.stockservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.dudi.stockservice.dto.OrderEvent;

@Slf4j
@Service
public class RabbitMQOrderConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.order.name}")
    public void consumer(OrderEvent orderEvent) {
        log.info(String.format("Order event received from RabbitMQ -> %s",orderEvent.toString()));

        // save order event data in database
    }

}
