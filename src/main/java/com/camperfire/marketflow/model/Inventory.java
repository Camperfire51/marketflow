package com.camperfire.marketflow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int stockQuantity; // Available stock
    private int reservedQuantity; // Quantity reserved for orders

    private int reorderLevel; // Minimum stock before restocking is required
}
