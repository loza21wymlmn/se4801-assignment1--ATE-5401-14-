// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalAmount;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setOrder(this);
        item.setUnitPrice(product.getPrice());

        this.items.add(item);
        BigDecimal itemTotal = item.getUnitPrice()
                .multiply(BigDecimal.valueOf(quantity));

        this.totalAmount = (this.totalAmount == null)
                ? itemTotal
                : this.totalAmount.add(itemTotal);
    }
}

