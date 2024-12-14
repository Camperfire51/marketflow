package com.camperfire.marketflow.model;

import com.camperfire.marketflow.model.user.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Long> products = new HashMap<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Address shippingAddress;
}
