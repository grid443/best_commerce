package com.best.commerce.product.core.util;

import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.type.DeliveryOptionType;
import com.best.commerce.product.api.v1.type.PaymentOptionType;
import com.best.commerce.product.api.v1.type.ProductCategory;
import com.best.commerce.product.core.entity.DeliveryOption;
import com.best.commerce.product.core.entity.PaymentOption;
import com.best.commerce.product.core.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Fixtures {
  private Fixtures() {}

  public static Product buildProduct(
      UUID merchantId,
      int inventory,
      String name,
      Set<PaymentOption> paymentOptions,
      Set<DeliveryOption> deliveryOptions) {
    return Product.builder()
        .merchantId(merchantId)
        .name(name)
        .description(randomString())
        .category(ProductCategory.FASHION)
        .unitPrice(randomMoney())
        .inventory(inventory)
        .paymentOptions(paymentOptions)
        .deliveryOptions(deliveryOptions)
        .build();
  }

  public static ProductDto buildProductDto(
      UUID merchantId,
      int inventory,
      String name,
      Set<PaymentOptionType> paymentOptions,
      Set<DeliveryOptionType> deliveryOptions) {
    return ProductDto.builder()
        .merchantId(merchantId)
        .name(name)
        .description(randomString())
        .category(ProductCategory.FASHION)
        .unitPrice(randomMoney())
        .inventory(inventory)
        .paymentOptions(paymentOptions)
        .deliveryOptions(deliveryOptions)
        .build();
  }

  public static PaymentOption buildPaymentOption(PaymentOptionType paymentOption) {
    return PaymentOption.builder().name(paymentOption).build();
  }

  public static DeliveryOption buildDeliveryOption(DeliveryOptionType deliveryOption) {
    return DeliveryOption.builder().name(deliveryOption).build();
  }

  private static String randomString() {
    return UUID.randomUUID().toString();
  }

  private static BigDecimal randomMoney() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10, 100))
        .setScale(2, RoundingMode.HALF_DOWN);
  }
}
