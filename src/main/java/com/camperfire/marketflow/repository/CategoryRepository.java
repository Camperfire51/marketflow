package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}