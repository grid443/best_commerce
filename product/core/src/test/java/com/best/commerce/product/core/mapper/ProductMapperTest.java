package com.best.commerce.product.core.mapper;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.api.type.PaymentOptionType;
import com.best.commerce.product.core.entity.DeliveryOption;
import com.best.commerce.product.core.entity.PaymentOption;
import com.best.commerce.product.core.entity.Product;
import com.best.commerce.product.core.util.Fixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.UUID;

class ProductMapperTest {
  private final ProductMapper productMapper = new ProductMapperImpl();

  @Test
  void productMappingTest() {
    // given
    PaymentOption directPayment = Fixtures.buildPaymentOption(PaymentOptionType.DIRECT);
    PaymentOption installmentsPayment = Fixtures.buildPaymentOption(PaymentOptionType.INSTALLMENTS);
    DeliveryOption directDelivery = Fixtures.buildDeliveryOption(DeliveryOptionType.DIRECT);
    DeliveryOption postamatDelivery = Fixtures.buildDeliveryOption(DeliveryOptionType.POSTAMAT);

    String productAName = "A";
    String productBName = "B";
    Product productA =
        Fixtures.buildProduct(
            UUID.randomUUID(),
            10,
            productAName,
            Set.of(directPayment, installmentsPayment),
            Set.of(directDelivery, postamatDelivery));

    Product productB =
        Fixtures.buildProduct(
            UUID.randomUUID(),
            10,
            productBName,
            Set.of(installmentsPayment),
            Set.of(directDelivery));

    // when
    List<ProductDto> products = productMapper.productsToDtoList(Set.of(productA, productB));

    // then
    Assert.notEmpty(products);
    Assertions.assertEquals(2, products.size());
    boolean productAFound = false;
    boolean productBFound = false;
    for (ProductDto product : products) {
      Set<PaymentOptionType> paymentOptions = product.getPaymentOptions();
      Set<DeliveryOptionType> deliveryOptions = product.getDeliveryOptions();
      Assert.notEmpty(paymentOptions);
      Assert.notEmpty(deliveryOptions);
      if (productAName.equals(product.getName())) {
        checkProduct(productA, product);
        Assertions.assertEquals(2, paymentOptions.size());
        Assertions.assertEquals(2, deliveryOptions.size());
        productAFound = true;
      } else if (productBName.equals(product.getName())) {
        checkProduct(productB, product);
        Assertions.assertEquals(1, paymentOptions.size());
        Assertions.assertEquals(1, deliveryOptions.size());
        Assertions.assertEquals(PaymentOptionType.INSTALLMENTS, paymentOptions.iterator().next());
        Assertions.assertEquals(DeliveryOptionType.DIRECT, deliveryOptions.iterator().next());
        productBFound = true;
      } else {
        Assertions.fail("Unexpected product: " + product.toString());
      }
    }
    Assertions.assertTrue(productAFound);
    Assertions.assertTrue(productBFound);
  }

  private void checkProduct(Product expectedProduct, ProductDto actualProduct) {
    Assertions.assertEquals(expectedProduct.getMerchantId(), actualProduct.getMerchantId());
    Assertions.assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
    Assertions.assertEquals(expectedProduct.getCategory(), actualProduct.getCategory());
    Assertions.assertEquals(expectedProduct.getUnitPrice(), actualProduct.getUnitPrice());
    Assertions.assertEquals(expectedProduct.getInventory(), actualProduct.getInventory());
  }
}
