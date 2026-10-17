package com.nilesh.commerce.order;

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

@RestController
@RequestMapping("/api/v1/orders")
class OrderController {

    private final OrderRepository orders;

    OrderController(OrderRepository orders) {
        this.orders = orders;
    }

    @GetMapping
    List<PurchaseOrder> all() {
        return orders.findAll();
    }

    @GetMapping("/{id}")
    PurchaseOrder one(@PathVariable String id) {
        return orders.findById(id).orElseThrow(() -> new OrderNotFound(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PurchaseOrder create(@Valid @RequestBody CreateOrder request) {
        return orders.save(new PurchaseOrder(request.customerId(), request.productId(), request.quantity(), request.totalAmount()));
    }

    record CreateOrder(@NotBlank String customerId, @NotBlank String productId, @Positive int quantity, @NotNull
            @Positive BigDecimal totalAmount) {}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class OrderNotFound extends RuntimeException {

        OrderNotFound(String id) {
            super("Order not found: " + id);
        }
    }
}
