package com.best.commerce.product.core.mapper;

import com.best.commerce.product.api.messaging.exchange.GetProductList;
import com.best.commerce.product.api.dto.ProductListRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ObjectMapperTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void getProductListMappingTest() throws JsonProcessingException {
    // given
    ProductListRequest payload =
        ProductListRequest.builder()
            .merchantId(UUID.randomUUID())
            .pageNumber(1)
            .pageSize(20)
            .sortField(ProductListRequest.SortField.INVENTORY)
            .build();
    GetProductList expectedRequest = new GetProductList(payload);
    String serializedRequest = mapper.writeValueAsString(expectedRequest);

    // when
    GetProductList deserializedRequest = mapper.readValue(serializedRequest, GetProductList.class);

    // then
    Assertions.assertEquals(expectedRequest, deserializedRequest);
  }
}
