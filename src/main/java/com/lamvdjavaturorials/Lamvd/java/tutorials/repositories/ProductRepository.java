package com.lamvdjavaturorials.Lamvd.java.tutorials.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamvdjavaturorials.Lamvd.java.tutorials.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByProductName(String productName);
}