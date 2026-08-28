package com.nilesh.commerce.product;
import jakarta.persistence.*; import java.math.BigDecimal;
@Entity @Table(name="products") class Product { @Id @GeneratedValue(strategy=GenerationType.UUID) String id; @Column(nullable=false) String name; @Column(nullable=false,precision=12,scale=2) BigDecimal price; @Column(nullable=false) int inventory; protected Product(){} Product(String name,BigDecimal price,int inventory){this.name=name;this.price=price;this.inventory=inventory;} }
