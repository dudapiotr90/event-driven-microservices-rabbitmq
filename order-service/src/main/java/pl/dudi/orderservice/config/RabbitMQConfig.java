package pl.dudi.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.order.name}")
    private String exchange;
    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;
    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;
    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;
    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    // 1. Spring Bean for queue - order queue
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    // 2. Spring Bean for exchange

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    // 3. Spring Bean for binding between exchange and queue using routing_key
    @Bean
    public Binding orderBinding() {
        return BindingBuilder
            .bind(orderQueue())
            .to(exchange())
            .with(orderRoutingKey);
    }
    @Bean
    public Binding emailBinding() {
        return BindingBuilder
            .bind(emailQueue())
            .to(exchange())
            .with(emailRoutingKey);
    }

    // 4. Message Converter
    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 5. Configure RabbitTemplate
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }

}
