package com.best.commerce.apigateway.service;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.dto.ProductListRequest;

import java.util.List;

public interface ProductService {
  void createProduct(ProductDto product);
  List<ProductDto> getMerchantProducts(ProductListRequest request);
}
