package com.nilesh.commerce.catalog;
import org.springframework.data.jpa.repository.JpaRepository;
interface ProductRepository extends JpaRepository<Product, String> {}
