package com.best.commerce.product.core.repository;

import com.best.commerce.product.api.type.DeliveryOptionType;
import com.best.commerce.product.core.entity.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, DeliveryOptionType> {
}
