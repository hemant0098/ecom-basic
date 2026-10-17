package com.nilesh.commerce.catalog;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    private final ProductRepository products;

    ProductController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping
    List<Product> all() {
        return products.findAll();
    }

    @GetMapping("/{id}")
    Product one(@PathVariable String id) {
        return products.findById(id).orElseThrow(() -> new ProductNotFound(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Product create(@Valid @RequestBody CreateProduct request) {
        return products.save(new Product(request.name(), request.price(), request.availableQuantity()));
    }

    record CreateProduct(@NotBlank String name, @NotNull
            @Positive BigDecimal price, @PositiveOrZero int availableQuantity) {}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class ProductNotFound extends RuntimeException {

        ProductNotFound(String id) {
            super("Product not found: " + id);
        }
    }
}
