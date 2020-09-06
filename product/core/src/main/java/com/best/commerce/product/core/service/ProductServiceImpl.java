package com.best.commerce.product.core.service;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.api.type.PaymentOptionType;
import com.best.commerce.product.core.entity.DeliveryOption;
import com.best.commerce.product.core.entity.PaymentOption;
import com.best.commerce.product.core.entity.Product;
import com.best.commerce.product.core.mapper.ProductMapper;
import com.best.commerce.product.core.repository.DeliveryOptionRepository;
import com.best.commerce.product.core.repository.PaymentOptionRepository;
import com.best.commerce.product.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private static final Integer INVENTORY_LIMIT = 4;

  private final ProductRepository productRepository;
  private final PaymentOptionRepository paymentOptionRepository;
  private final DeliveryOptionRepository deliveryOptionRepository;
  private final ProductMapper productMapper;

  @Override
  @Transactional
  public ProductDto createProduct(ProductDto product) {
    Product productEntity = productMapper.toEntity(product);
    mergePaymentOptions(productEntity);
    mergeDeliveryOptions(productEntity);
    Product savedProduct = productRepository.save(productEntity);
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

  private void mergePaymentOptions(Product product) {
    Set<PaymentOption> paymentOptions = product.getPaymentOptions();
    if (CollectionUtils.isEmpty(paymentOptions)) {
      return;
    }
    Set<PaymentOptionType> paymentOptionTypes =
        paymentOptions.stream().map(PaymentOption::getName).collect(Collectors.toSet());
    List<PaymentOption> existingPaymentOptions =
        paymentOptionRepository.findAllById(paymentOptionTypes);
    if (CollectionUtils.isEmpty(existingPaymentOptions)) {
      return;
    }
    if (paymentOptions.size() == existingPaymentOptions.size()) {
      product.setPaymentOptions(Set.copyOf(existingPaymentOptions));
      return;
    }
    Map<PaymentOptionType, PaymentOption> paymentOptionById =
        existingPaymentOptions.stream()
            .collect(Collectors.toMap(PaymentOption::getName, Function.identity()));
    Set<PaymentOption> updatedPaymentOptions = new HashSet<>();
    for (PaymentOption paymentOption : paymentOptions) {
      PaymentOption updatedOption =
          paymentOptionById.computeIfAbsent(paymentOption.getName(), (key) -> paymentOption);
      updatedPaymentOptions.add(updatedOption);
    }
    product.setPaymentOptions(updatedPaymentOptions);
  }

  private void mergeDeliveryOptions(Product product) {
    Set<DeliveryOption> deliveryOptions = product.getDeliveryOptions();
    if (CollectionUtils.isEmpty(deliveryOptions)) {
      return;
    }
    Set<DeliveryOptionType> deliveryOptionTypes =
        deliveryOptions.stream().map(DeliveryOption::getName).collect(Collectors.toSet());
    List<DeliveryOption> existingDeliveryOptions =
        deliveryOptionRepository.findAllById(deliveryOptionTypes);
    if (CollectionUtils.isEmpty(existingDeliveryOptions)) {
      return;
    }
    if (deliveryOptions.size() == existingDeliveryOptions.size()) {
      product.setDeliveryOptions(Set.copyOf(existingDeliveryOptions));
      return;
    }
    Map<DeliveryOptionType, DeliveryOption> deliveryOptionById =
        existingDeliveryOptions.stream()
            .collect(Collectors.toMap(DeliveryOption::getName, Function.identity()));
    Set<DeliveryOption> updatedDeliveryOptions = new HashSet<>();
    for (DeliveryOption deliveryOption : deliveryOptions) {
      DeliveryOption updatedOption =
          deliveryOptionById.computeIfAbsent(deliveryOption.getName(), (key) -> deliveryOption);
      updatedDeliveryOptions.add(updatedOption);
    }
    product.setDeliveryOptions(updatedDeliveryOptions);
  }
}
