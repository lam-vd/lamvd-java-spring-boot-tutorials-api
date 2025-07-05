package com.lamvdjavaturorials.Lamvd.java.tutorials.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true, length = 300)
  private String productName;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  private int year;
  private Double price;
  private String url;

  // Default constructor for JPA
  public Product() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Product(String productName, int year, Double price, String url) {
    this.productName = productName;
    this.year = year;
    this.price = price;
    this.url = url;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", ProductName='" + productName + '\'' +
        ", year=" + year +
        ", price=" + price +
        ", url='" + url + '\'' +
        '}';
  }
}