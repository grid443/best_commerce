package com.best.commerce.product.core.service;

import com.best.commerce.product.api.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
  ProductDto createProduct(ProductDto productDto);

  List<ProductDto> getMerchantProducts(UUID merchantId, Pageable pageable);
}
