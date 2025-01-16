package com.api.EcomTracker.domain.order;

import com.api.EcomTracker.domain.products.ProductsRegisterData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDTO {
    @NotEmpty(message = "Products list cannot be empty ")
    private List<ProductOrderDTO> products;


}