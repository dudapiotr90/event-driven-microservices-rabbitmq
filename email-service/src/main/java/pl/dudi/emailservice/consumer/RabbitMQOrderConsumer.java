package pl.dudi.emailservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.dudi.emailservice.dto.OrderEvent;

@Slf4j
@Service
public class RabbitMQOrderConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consumeEvent(OrderEvent orderEvent) {
        log.info(String.format("Order event received from RabbitMQ -> %s", orderEvent));

        // send an email
    }
}
