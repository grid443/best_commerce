package com.best.commerce.product.core.repository;

import com.best.commerce.product.api.v1.type.PaymentOptionType;
import com.best.commerce.product.core.entity.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOptionRepository extends JpaRepository<PaymentOption, PaymentOptionType> {
}
