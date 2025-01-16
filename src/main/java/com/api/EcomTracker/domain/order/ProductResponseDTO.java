package com.api.EcomTracker.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private BigDecimal valueEach;

    public ProductResponseDTO(Long productId, String productName, BigDecimal valueEach) {
        this.productId = productId;
        this.productName = productName;
        this.valueEach = valueEach;
    }
}
