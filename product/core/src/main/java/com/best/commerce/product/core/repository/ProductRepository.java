package com.best.commerce.product.core.repository;

import com.best.commerce.product.core.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findAllByMerchantIdAndInventoryGreaterThan(
      UUID merchantId, Integer inventoryLimit, Pageable pageable);
}
