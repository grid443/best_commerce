package com.best.commerce.product.core.entity;

import com.best.commerce.product.api.v1.type.PaymentOptionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment_option")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentOption {
  @Id
  @Enumerated(EnumType.STRING)
  private PaymentOptionType name;
}
