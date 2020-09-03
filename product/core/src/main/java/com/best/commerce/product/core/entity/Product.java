package com.best.commerce.product.core.entity;

import com.best.commerce.product.api.type.ProductCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
  @Id
  @GeneratedValue
  @Getter(AccessLevel.NONE)
  private UUID id;

  @Version
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Integer version;

  @Column(name = "merchant_id", nullable = false)
  private UUID merchantId;

  @Column(name = "name", nullable = false, length = 64)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "category", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductCategory category;

  @Column(name = "unit_price", precision = 18, scale = 2)
  private BigDecimal unitPrice;

  @Column(name = "inventory", nullable = false)
  private Integer inventory;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "product_payment_option",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "payment_option_name"))
  private Set<PaymentOption> paymentOptions;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "product_delivery_option",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "delivery_option_name"))
  private Set<DeliveryOption> deliveryOptions;
}
