package com.nilesh.commerce.catalog;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name = "products") public class Product {
 @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;
 @Column(nullable = false) private String name;
 @Column(nullable = false, precision = 12, scale = 2) private BigDecimal price;
 @Column(nullable = false) private int availableQuantity;
 protected Product() {} Product(String name, BigDecimal price, int availableQuantity) { this.name=name; this.price=price; this.availableQuantity=availableQuantity; }
 public String getId(){return id;} public String getName(){return name;} public BigDecimal getPrice(){return price;} public int getAvailableQuantity(){return availableQuantity;}
}
