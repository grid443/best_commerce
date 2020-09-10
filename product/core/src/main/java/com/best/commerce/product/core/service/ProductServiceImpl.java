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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    if (CollectionUtils.isEmpty(product.getPaymentOptions())) {
      return;
    }
    List<PaymentOption> existingPaymentOptions =
        findExistingPaymentOptions(product.getPaymentOptions());
    if (CollectionUtils.isEmpty(existingPaymentOptions)) {
      return;
    }
    boolean allOptionsExists =
        (product.getPaymentOptions().size() == existingPaymentOptions.size());
    if (allOptionsExists) {
      product.setPaymentOptions(Set.copyOf(existingPaymentOptions));
      return;
    }
    mergeExistingPaymentOptions(product, existingPaymentOptions);
  }

  private List<PaymentOption> findExistingPaymentOptions(Set<PaymentOption> paymentOptions) {
    Set<PaymentOptionType> paymentOptionIds =
        paymentOptions.stream().map(PaymentOption::getName).collect(Collectors.toSet());
    return paymentOptionRepository.findAllById(paymentOptionIds);
  }

  private void mergeExistingPaymentOptions(Product product, List<PaymentOption> existingOptions) {
    Map<PaymentOptionType, PaymentOption> existingPaymentOptionById =
        existingOptions.stream()
            .collect(Collectors.toMap(PaymentOption::getName, Function.identity()));
    Set<PaymentOption> updatedPaymentOptions =
        product.getPaymentOptions().stream()
            .map(
                paymentOption ->
                    existingPaymentOptionById.computeIfAbsent(
                        paymentOption.getName(), (key) -> paymentOption))
            .collect(Collectors.toSet());
    product.setPaymentOptions(updatedPaymentOptions);
  }

  private void mergeDeliveryOptions(Product product) {
    if (CollectionUtils.isEmpty(product.getDeliveryOptions())) {
      return;
    }
    List<DeliveryOption> existingOptions =
        findExistingDeliveryOptions(product.getDeliveryOptions());
    if (CollectionUtils.isEmpty(existingOptions)) {
      return;
    }
    boolean allOptionsExists = (product.getDeliveryOptions().size() == existingOptions.size());
    if (allOptionsExists) {
      product.setDeliveryOptions(Set.copyOf(existingOptions));
      return;
    }
    mergeExistingDeliveryOptions(product, existingOptions);
  }

  private void mergeExistingDeliveryOptions(Product product, List<DeliveryOption> existingOptions) {
    Map<DeliveryOptionType, DeliveryOption> existingDeliveryOptionById =
        existingOptions.stream()
            .collect(Collectors.toMap(DeliveryOption::getName, Function.identity()));
    Set<DeliveryOption> updatedDeliveryOptions =
        product.getDeliveryOptions().stream()
            .map(
                deliveryOption ->
                    existingDeliveryOptionById.computeIfAbsent(
                        deliveryOption.getName(), (key) -> deliveryOption))
            .collect(Collectors.toSet());
    product.setDeliveryOptions(updatedDeliveryOptions);
  }

  private List<DeliveryOption> findExistingDeliveryOptions(Set<DeliveryOption> deliveryOptions) {
    Set<DeliveryOptionType> deliveryOptionIds =
        deliveryOptions.stream().map(DeliveryOption::getName).collect(Collectors.toSet());
    return deliveryOptionRepository.findAllById(deliveryOptionIds);
  }
}
