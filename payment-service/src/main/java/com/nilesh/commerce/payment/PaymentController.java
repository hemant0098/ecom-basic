package com.nilesh.commerce.payment;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.math.BigDecimal; import java.util.UUID;
@RestController @RequestMapping("/api/v1/payments") class PaymentController { @PostMapping @ResponseStatus(HttpStatus.CREATED) PaymentResponse process(@Valid @RequestBody PaymentRequest request){return new PaymentResponse(UUID.randomUUID().toString(),request.orderId(),"APPROVED");} }
record PaymentRequest(@NotBlank String orderId,@NotNull @Positive BigDecimal amount){} record PaymentResponse(String transactionId,String orderId,String status){}
