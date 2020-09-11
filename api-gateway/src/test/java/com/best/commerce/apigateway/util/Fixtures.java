package com.best.commerce.apigateway.util;

import com.best.commerce.product.api.v1.dto.ProductDto;
import com.best.commerce.product.api.v1.type.DeliveryOptionType;
import com.best.commerce.product.api.v1.type.PaymentOptionType;
import com.best.commerce.product.api.v1.type.ProductCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Fixtures {
  private Fixtures() {}

  public static ProductDto buildProductDto(
      Set<PaymentOptionType> paymentOptions, Set<DeliveryOptionType> deliveryOptions) {
    return ProductDto.builder()
        .merchantId(UUID.randomUUID())
        .name(randomString())
        .description(randomString())
        .category(ProductCategory.FASHION)
        .unitPrice(randomMoney())
        .inventory(ThreadLocalRandom.current().nextInt(1, 10))
        .paymentOptions(paymentOptions)
        .deliveryOptions(deliveryOptions)
        .build();
  }

  private static String randomString() {
    return UUID.randomUUID().toString();
  }

  private static BigDecimal randomMoney() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10, 100))
        .setScale(2, RoundingMode.HALF_DOWN);
  }
}
