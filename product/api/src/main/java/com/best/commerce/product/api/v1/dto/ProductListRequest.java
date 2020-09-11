package com.best.commerce.product.api.v1.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductListRequest {
  @NotNull private UUID merchantId;

  @Min(0)
  private int pageNumber;

  @Min(0)
  private int pageSize;

  @NotNull private SortField sortField;

  @RequiredArgsConstructor
  @Getter
  public enum SortField {
    UNIT_PRICE("unitPrice"),
    INVENTORY("inventory");

    private final String fieldName;
  }
}
