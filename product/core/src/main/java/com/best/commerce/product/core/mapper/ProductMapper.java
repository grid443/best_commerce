package com.best.commerce.product.core.mapper;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.api.type.PaymentOptionType;
import com.best.commerce.product.core.entity.DeliveryOption;
import com.best.commerce.product.core.entity.PaymentOption;
import com.best.commerce.product.core.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  @Mapping(
      target = "paymentOptions",
      source = "paymentOptions",
      qualifiedByName = "paymentOptionsToDto")
  @Mapping(
      target = "deliveryOptions",
      source = "deliveryOptions",
      qualifiedByName = "deliveryOptionToDto")
  public abstract ProductDto toDto(Product product);

  public abstract List<ProductDto> productsToDtoList(Collection<Product> products);

  @Named("paymentOptionToDto")
  public Set<PaymentOptionType> paymentOptionsToDto(Set<PaymentOption> paymentOptions) {
    if (CollectionUtils.isEmpty(paymentOptions)) {
      return Collections.emptySet();
    }
    return paymentOptions.stream().map(PaymentOption::getName).collect(Collectors.toSet());
  }

  @Named("deliveryOptionToDto")
  public Set<DeliveryOptionType> deliveryOptionsToDto(Set<DeliveryOption> deliveryOptions) {
    if (CollectionUtils.isEmpty(deliveryOptions)) {
      return Collections.emptySet();
    }
    return deliveryOptions.stream().map(DeliveryOption::getName).collect(Collectors.toSet());
  }
}
