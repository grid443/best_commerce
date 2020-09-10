package com.best.commerce.product.api.dto;

import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.api.type.PaymentOptionType;
import com.best.commerce.product.api.type.ProductCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {
  private UUID id;
  @NotNull private UUID merchantId;
  @NotEmpty private String name;
  private String description;
  @NotNull private ProductCategory category;
  private BigDecimal unitPrice;

  @NotNull
  @Min(0)
  private Integer inventory;

  private Set<PaymentOptionType> paymentOptions;
  private Set<DeliveryOptionType> deliveryOptions;
}
