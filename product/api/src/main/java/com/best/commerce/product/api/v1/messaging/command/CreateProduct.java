package com.best.commerce.product.api.v1.messaging.command;

import com.best.commerce.messaging.api.v1.Command;
import com.best.commerce.product.api.v1.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProduct implements Command<ProductDto> {
  public static final String QUEUE = "product.create.queue";

  private ProductDto product;

  @Override
  public ProductDto payload() {
    return product;
  }

  @Override
  public String queue() {
    return QUEUE;
  }
}
