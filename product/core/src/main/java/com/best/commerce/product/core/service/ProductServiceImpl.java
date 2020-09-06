package com.best.commerce.product.core.service;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.core.entity.Product;
import com.best.commerce.product.core.mapper.ProductMapper;
import com.best.commerce.product.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private static final Integer INVENTORY_LIMIT = 4;

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  @Transactional
  public ProductDto createProduct(ProductDto product) {
    Product savedProduct = productRepository.save(productMapper.toEntity(product));
    return productMapper.toDto(savedProduct);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDto> getMerchantProducts(UUID merchantId, Pageable pageable) {
    List<Product> productList =
        productRepository.findAllByMerchantIdAndInventoryGreaterThan(
            merchantId, INVENTORY_LIMIT, pageable);
    return productMapper.productsToDtoList(productList);
  }
}
