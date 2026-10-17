package com.nilesh.commerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<PurchaseOrder, String> {
}
