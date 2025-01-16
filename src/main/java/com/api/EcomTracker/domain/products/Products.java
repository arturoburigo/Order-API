package com.api.EcomTracker.domain.products;

import com.api.EcomTracker.domain.categories.Categories;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "products")
@Entity(name = "Products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String color;
    @Column(nullable = true)
    private String size;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer quantity;
    private Boolean active;
    @Column(nullable = false)
    private Integer reservedQuantity = 0;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    public Products(ProductsRegisterData data, Categories category) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.color = data.getColor();
        this.size = data.getSize();
        this.price = data.getPrice();
        this.quantity = data.getQuantity();
        this.reservedQuantity =0;
        this.active = true;
        this.category = category;
    }

    public int getAvailableStock(){
        return this.quantity - this.reservedQuantity;
    }

    public void reserveStock(int quantityToReserve) {
        if (quantityToReserve > getAvailableStock()) {
            throw new IllegalArgumentException("Cannot reserve more than the available stock");
        }
        this.reservedQuantity += quantityToReserve;
        this.quantity = getAvailableStock();
    }

    public void updateActive(Boolean active) {
        this.active = active;
    }
}