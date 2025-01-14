package com.api.EcomTracker.domain.order;

import com.api.EcomTracker.domain.products.Products;
import com.api.EcomTracker.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private Long productId;
    private Long userId;
    private BigDecimal amount;
    private Integer quantity;
    private OrderStatusDTO status;
    private LocalDateTime createdAt;

    public OrderDTO(String id, Products product, Users user, BigDecimal totalPrice, Integer quantity, OrderStatusDTO orderStatusDTO) {
        this.id  = id;
        this.productId = product.getId();
        this.userId = user.getId();
        this.amount = totalPrice;
        this.quantity = quantity;
        this.status = orderStatusDTO;
        this.createdAt = LocalDateTime.now();
    }

}
