package com.best.commerce.product.api.v1.messaging.exchange;

import com.best.commerce.messaging.api.v1.Exchange;
import com.best.commerce.product.api.v1.dto.ProductListRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetProductList implements Exchange<ProductListRequest> {
  public static final String ROUTING_KEY = "product.list";
  public static final String EXCHANGE = "product.list.exchange";
  public static final String QUEUE = "product.list.queue";

  private ProductListRequest productListRequest;

  @Override
  public ProductListRequest payload() {
    return productListRequest;
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
