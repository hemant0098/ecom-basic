package com.nilesh.commerce.product;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.Instant;
@RestControllerAdvice class ApiExceptionHandler { @ExceptionHandler(NotFoundException.class) ResponseEntity<ApiError> notFound(NotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(Instant.now(),e.getMessage()));} } class NotFoundException extends RuntimeException { NotFoundException(String message){super(message);} } record ApiError(Instant timestamp,String message){}
