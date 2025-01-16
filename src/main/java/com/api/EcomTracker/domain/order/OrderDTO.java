package com.api.EcomTracker.domain.order;

import com.api.EcomTracker.domain.products.Products;
import com.api.EcomTracker.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private Long userId;
    private BigDecimal amount;
    private OrderStatusDTO status;
    private List<ProductOrderDTO> items;
    private LocalDateTime createdAt;

    public OrderDTO(String id, Users user, BigDecimal totalPrice, List<ProductOrderDTO> items, OrderStatusDTO orderStatusDTO) {
        this.id  = id;
        this.userId = user.getId();
        this.items = items;
        this.amount = totalPrice;
        this.status = orderStatusDTO;
        this.createdAt = LocalDateTime.now();
    }

}
