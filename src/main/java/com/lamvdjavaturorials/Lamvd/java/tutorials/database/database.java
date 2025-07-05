package com.lamvdjavaturorials.Lamvd.java.tutorials.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lamvdjavaturorials.Lamvd.java.tutorials.models.Product;
import com.lamvdjavaturorials.Lamvd.java.tutorials.repositories.ProductRepository;

@Configuration
public class database {
  // Logger to log messages
  private static final Logger logger = LoggerFactory.getLogger(database.class);

  @Bean
  CommandLineRunner initDatabase(ProductRepository productRepository) {
    return args -> {
      saveIfNotExists(productRepository,
          new Product("iPhone 14 Pro", 2022, 999.99, "https://www.apple.com/iphone-14-pro"));
      saveIfNotExists(productRepository, new Product("iPad Pro", 2022, 799.99, "https://www.apple.com/ipad-pro"));
      saveIfNotExists(productRepository,
          new Product("MacBook Pro", 2022, 1999.99, "https://www.apple.com/macbook-pro"));
      logger.info("Database initialized with products: {}", productRepository.findAll());
    };
  }

  private void saveIfNotExists(ProductRepository repo, Product product) {
    if (repo.findAll().stream().noneMatch(
        p -> p.getProductName() != null && p.getProductName().equals(product.getProductName()))) {
      repo.save(product);
    } else {
      logger.info("Product already exists: {}", product.getProductName());
    }
  }
}
