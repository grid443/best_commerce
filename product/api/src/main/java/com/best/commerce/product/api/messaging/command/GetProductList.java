package com.best.commerce.product.api.messaging.command;

import com.best.commerce.messaging.api.Exchange;
import com.best.commerce.product.api.messaging.payload.ProductListRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetProductList implements Exchange<ProductListRequest> {
  public static final String ROUTING_KEY = "product.list";
  public static final String EXCHANGE = "product.list.exchange";
  public static final String QUEUE = "product.list.queue";

  private ProductListRequest pageable;

  @Override
  public ProductListRequest payload() {
    return pageable;
  }

  @Override
  public String queue() {
    return QUEUE;
  }

  @Override
  public String exchange() {
    return EXCHANGE;
  }

  @Override
  public String routingKey() {
    return ROUTING_KEY;
  }
}
