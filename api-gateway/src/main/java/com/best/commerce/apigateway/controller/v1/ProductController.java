package com.best.commerce.apigateway.controller.v1;

import com.best.commerce.apigateway.service.ProductService;
import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.dto.ProductListRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "${cors.allowed-origins}")
@RestController
@RequestMapping(path = "/v1/product")
@RequiredArgsConstructor
@Api("Product operations")
public class ProductController {

  private final ProductService productService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Create new product")
  public void createProduct(@Valid @RequestBody ProductDto product) {
    productService.createProduct(product);
  }

  @PostMapping(
      path = "/list",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(
      value = "Get list of products",
      response = ProductDto.class,
      responseContainer = "list")
  public List<ProductDto> getMerchantProducts(@Valid @RequestBody ProductListRequest request) {
    return productService.getMerchantProducts(request);
  }
}
