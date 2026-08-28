package com.nilesh.commerce.catalog;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; import java.util.List;
@RestController @RequestMapping("/api/v1/products") class ProductController {
 private final ProductRepository products; ProductController(ProductRepository products){this.products=products;}
 @GetMapping List<Product> all(){return products.findAll();}
 @GetMapping("/{id}") Product one(@PathVariable String id){return products.findById(id).orElseThrow(()->new ProductNotFound(id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) Product create(@Valid @RequestBody CreateProduct request){return products.save(new Product(request.name(),request.price(),request.availableQuantity()));}
 record CreateProduct(@NotBlank String name,@NotNull @Positive BigDecimal price,@PositiveOrZero int availableQuantity) {}
 @ResponseStatus(HttpStatus.NOT_FOUND) static class ProductNotFound extends RuntimeException { ProductNotFound(String id){super("Product not found: "+id);} }
}
