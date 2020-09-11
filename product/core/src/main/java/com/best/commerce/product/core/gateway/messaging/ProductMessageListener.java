package com.best.commerce.product.core.gateway.messaging;

import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.dto.ProductListRequest;
import com.best.commerce.product.api.v1.messaging.command.CreateProduct;
import com.best.commerce.product.api.v1.messaging.exchange.GetProductList;
import com.best.commerce.product.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMessageListener {
  private final ProductService productService;

  @RabbitListener(queues = CreateProduct.QUEUE)
  public void createProduct(CreateProduct request) {
    log.info("create product request {}", request);
    ProductDto product = request.payload();
    ProductDto savedProduct = productService.createProduct(product);
    log.info("product {} created", savedProduct);
  }

  @RabbitListener(queues = GetProductList.QUEUE)
  public List<ProductDto> getMerchantProducts(GetProductList request) {
    log.info("merchant product list request {}", request);
    ProductListRequest payload = request.payload();
    Pageable pageable =
        PageRequest.of(
            payload.getPageNumber(),
            payload.getPageSize(),
            Sort.by(payload.getSortField().getFieldName()));
    List<ProductDto> merchantProducts =
        productService.getMerchantProducts(payload.getMerchantId(), pageable);
    log.info("merchant product list request: {} response: {}", request, merchantProducts);
    return merchantProducts;
  }
}
