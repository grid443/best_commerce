package com.best.commerce.product.core.gateway.messaging;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.messaging.command.GetProductList;
import com.best.commerce.product.api.messaging.payload.ProductListRequest;
import com.best.commerce.product.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMessageListener {
  private final ProductService productService;

  @RabbitListener(queues = GetProductList.QUEUE)
  public List<ProductDto> getMerchantProducts(ProductListRequest request) {
    Pageable pageable =
        PageRequest.of(
            request.getPageNumber(),
            request.getPageSize(),
            Sort.by(request.getSortField().getFieldName()));
    return productService.getMerchantProducts(request.getMerchantId(), pageable);
  }
}
