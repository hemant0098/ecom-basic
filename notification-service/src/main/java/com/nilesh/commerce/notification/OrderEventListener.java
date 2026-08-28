package com.nilesh.commerce.notification;
import org.springframework.kafka.annotation.KafkaListener; import org.springframework.stereotype.Component;
@Component class OrderEventListener { @KafkaListener(topics="order-status",groupId="notification-service") void onOrderStatus(String event){System.out.println("Notification event received: "+event);} }
