package com.best.commerce.product.core.config;

import com.best.commerce.product.api.messaging.command.GetProductList;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public DirectExchange productListExchange() {
    return new DirectExchange(GetProductList.EXCHANGE);
  }

  @Bean
  public Queue productListQueue() {
    return new Queue(GetProductList.QUEUE, false);
  }

  @Bean
  public Binding productListBinding(DirectExchange exchange, Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with(GetProductList.ROUTING_KEY);
  }
}
