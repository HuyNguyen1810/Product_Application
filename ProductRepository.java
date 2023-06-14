package com.tutorial.apidemo.repository;

import com.tutorial.apidemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository<"Entity's type", "primary's key"> contain a lot of functions (post, delete, put, get)
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}
