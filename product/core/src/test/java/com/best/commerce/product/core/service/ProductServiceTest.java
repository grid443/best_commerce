package com.best.commerce.product.core.service;

import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.dto.ProductListRequest;
import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.api.type.PaymentOptionType;
import com.best.commerce.product.core.ProductApplication;
import com.best.commerce.product.core.entity.DeliveryOption;
import com.best.commerce.product.core.entity.PaymentOption;
import com.best.commerce.product.core.mapper.ProductMapperImpl;
import com.best.commerce.product.core.repository.ProductRepository;
import com.best.commerce.product.core.util.Fixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(
    classes = {ProductApplication.class, ProductServiceImpl.class, ProductMapperImpl.class})
public class ProductServiceTest {
  @Autowired private ProductService productService;
  @Autowired private ProductRepository productRepository;

  @BeforeEach
  public void clearRepository() {
    productRepository.deleteAll();
  }

  @Test
  public void pagedResultSortByInventoryTest() {
    // given
    UUID merchantId = UUID.randomUUID();
    Set<PaymentOption> paymentOptions =
        Set.of(Fixtures.buildPaymentOption(PaymentOptionType.DIRECT));
    Set<DeliveryOption> deliveryOptions =
        Set.of(Fixtures.buildDeliveryOption(DeliveryOptionType.POSTAMAT));
    var unexpectedProduct =
        Fixtures.buildProduct(merchantId, 4, "X", paymentOptions, deliveryOptions);
    var productA = Fixtures.buildProduct(merchantId, 5, "A", paymentOptions, deliveryOptions);
    var productB = Fixtures.buildProduct(merchantId, 6, "B", paymentOptions, deliveryOptions);
    var productC = Fixtures.buildProduct(merchantId, 7, "C", paymentOptions, deliveryOptions);
    productRepository.saveAll(List.of(unexpectedProduct, productA, productB, productC));
    var resultSort = Sort.by(ProductListRequest.SortField.INVENTORY.getFieldName());
    var firstPageRequest = PageRequest.of(0, 2, resultSort);
    var secondPageRequest = PageRequest.of(1, 2, resultSort);

    // when
    var firstPage = productService.getMerchantProducts(merchantId, firstPageRequest);
    var secondPage = productService.getMerchantProducts(merchantId, secondPageRequest);

    Assert.notEmpty(firstPage);
    Assert.notEmpty(secondPage);
    Assertions.assertEquals(2, firstPage.size());
    var firstPageProductNames =
        firstPage.stream().map(ProductDto::getName).collect(Collectors.toSet());
    Assertions.assertEquals(2, firstPageProductNames.size());
    Assertions.assertTrue(firstPageProductNames.contains("A"));
    Assertions.assertTrue(firstPageProductNames.contains("B"));
    Assertions.assertEquals(1, secondPage.size());
    Assertions.assertEquals("C", secondPage.get(0).getName());
    checkProductOptions(firstPage.get(0));
    checkProductOptions(firstPage.get(1));
    checkProductOptions(secondPage.get(0));
  }

  private void checkProductOptions(ProductDto product) {
    Set<PaymentOptionType> paymentOptions = product.getPaymentOptions();
    Assert.notEmpty(paymentOptions);
    Assertions.assertEquals(1, paymentOptions.size());
    Assertions.assertEquals(PaymentOptionType.DIRECT, paymentOptions.iterator().next());

    Set<DeliveryOptionType> deliveryOptions = product.getDeliveryOptions();
    Assert.notEmpty(deliveryOptions);
    Assertions.assertEquals(1, deliveryOptions.size());
    Assertions.assertEquals(DeliveryOptionType.POSTAMAT, deliveryOptions.iterator().next());
  }
}
