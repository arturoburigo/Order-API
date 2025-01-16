package com.api.EcomTracker.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderResponseDTO {
    private String id;
    private List<ProductResponseDTO> products; // Lista de produtos no pedido
    private Long userId;
    private BigDecimal amount;
    private Integer quantity;
    private OrderStatusDTO status;
    private LocalDateTime createdAt;
}
