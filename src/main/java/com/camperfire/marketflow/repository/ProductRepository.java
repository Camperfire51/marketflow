package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    //TODO: JQL ile filter nasıl yapılır ? Araştır.
}
