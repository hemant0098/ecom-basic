package com.nilesh.commerce.product;
import org.springframework.data.jpa.repository.JpaRepository;
interface ProductRepository extends JpaRepository<Product,String>{}
