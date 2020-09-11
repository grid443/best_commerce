package com.best.commerce.product.core.service;

import com.best.commerce.product.api.v1.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/** Service that manages merchant's products */
public interface ProductService {
  /** Create new product */
  ProductDto createProduct(ProductDto productDto);

  /**
   * Get page of merchant's products
   *
   * @param merchantId product owner ID
   * @param pageable paging request
   * @return found products
   */
  List<ProductDto> getMerchantProducts(UUID merchantId, Pageable pageable);
}
