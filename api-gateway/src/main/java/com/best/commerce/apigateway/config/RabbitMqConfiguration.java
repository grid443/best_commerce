package com.best.commerce.apigateway.config;

import com.best.commerce.product.api.messaging.exchange.GetProductList;
import org.springframework.amqp.core.DirectExchange;
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
}
