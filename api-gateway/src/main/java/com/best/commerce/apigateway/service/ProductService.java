package com.best.commerce.apigateway.service;

import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.dto.ProductListRequest;

import java.util.List;

/** Service that manages merchant's products */
public interface ProductService {
  /** Create new product */
  void createProduct(ProductDto product);

  /**
   * Get page of merchant's products
   *
   * @param request product filter
   * @return found products
   */
  List<ProductDto> getMerchantProducts(ProductListRequest request);
}
