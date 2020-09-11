package com.best.commerce.merchant.core.entity;

import com.best.commerce.inventory.api.v1.type.MerchantType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.UUID;

@Entity
@Table(name = "merchant")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Merchant {
  @Id
  @GeneratedValue
  @Getter(AccessLevel.NONE)
  private UUID id;

  @Version
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Integer version;

  @Column(name = "login", nullable = false, length = 32)
  private String login;

  @Column(name = "password_hash", nullable = false, length = 64)
  private String passwordHash;

  @Column(name = "password_salt", nullable = false, length = 64)
  private String passwordSalt;

  @Column(name = "type", nullable = false, length = 64)
  private MerchantType type;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "owner_name", nullable = false)
  private String ownerName;
}
