package com.best.commerce.product.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductListRequest {
  private UUID merchantId;
  private int pageNumber;
  private int pageSize;
  private SortField sortField;
  
  @RequiredArgsConstructor
  @Getter
  public enum SortField {
    UNIT_PRICE("unitPrice"),
    INVENTORY("inventory");
    
    private final String fieldName;
  }
}
