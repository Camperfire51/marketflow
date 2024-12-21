package com.camperfire.marketflow.model;

import com.camperfire.marketflow.model.user.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private Customer  customer;

    @ElementCollection(fetch = FetchType.LAZY) //TODO: Eager ve Lazy arasındaki fark.
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Long> products = new HashMap<>(); //TODO: Replace Product with product id. Since Hashcode is not implemented in products, map will be wrong each time products are loaded from the database.
}
