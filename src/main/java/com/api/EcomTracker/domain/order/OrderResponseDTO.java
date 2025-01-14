package com.api.EcomTracker.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String productName;
    private String username;
    private BigDecimal amount;
    private Integer quantity;
    private OrderStatusDTO status;
}