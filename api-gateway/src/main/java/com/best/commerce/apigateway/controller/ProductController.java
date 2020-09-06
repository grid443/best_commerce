package com.best.commerce.apigateway.controller;

import com.best.commerce.apigateway.service.ProductService;
import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.dto.ProductListRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public void createProduct(ProductDto product) {
    productService.createProduct(product);
  }

  @PostMapping("/list")
  @ApiOperation(
      value = "Get list of products",
      response = ProductDto.class,
      responseContainer = "list")
  public List<ProductDto> getMerchantProducts(@RequestBody ProductListRequest request) {
    return productService.getMerchantProducts(request);
  }
}
