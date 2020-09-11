package com.best.commerce.apigateway.service;

import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.dto.ProductListRequest;
import com.best.commerce.product.api.v1.messaging.command.CreateProduct;
import com.best.commerce.product.api.v1.messaging.exchange.GetProductList;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final RabbitTemplate template;

  @Override
  public void createProduct(ProductDto product) {
    CreateProduct command = new CreateProduct(product);
    template.convertAndSend(command.queue(), command);
  }

  @Override
  public List<ProductDto> getMerchantProducts(ProductListRequest payload) {
    GetProductList exchangeRequest = new GetProductList(payload);
    @SuppressWarnings("unchecked")
    List<ProductDto> response =
        (List<ProductDto>)
            template.convertSendAndReceive(
                exchangeRequest.exchange(), exchangeRequest.routingKey(), exchangeRequest);
    return response;
  }
}
