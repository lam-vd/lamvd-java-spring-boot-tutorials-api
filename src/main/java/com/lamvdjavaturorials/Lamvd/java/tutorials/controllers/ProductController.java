package com.lamvdjavaturorials.Lamvd.java.tutorials.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamvdjavaturorials.Lamvd.java.tutorials.models.Product;
import com.lamvdjavaturorials.Lamvd.java.tutorials.models.ResponseObject;
import com.lamvdjavaturorials.Lamvd.java.tutorials.repositories.ProductRepository;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
    return productRepository.findById(id)
        .map(product -> ResponseEntity.ok(
            new ResponseObject("ok", "Query product successfully", product)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseObject("failed", "Cannot find product with id = " + id, "")));
  }

  // insert a new product
  @PostMapping("/insert")
  public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
    List<Product> foundProducts = productRepository.findByProductName(newProduct.getProductName().trim());
    if (foundProducts.size() > 0) {
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
          .body(new ResponseObject("failed", "Product name already exists", ""));
    }
    newProduct.setId(null);
    Product savedProduct = productRepository.save(newProduct);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseObject("ok", "Insert product successfully", savedProduct));
  }
}
