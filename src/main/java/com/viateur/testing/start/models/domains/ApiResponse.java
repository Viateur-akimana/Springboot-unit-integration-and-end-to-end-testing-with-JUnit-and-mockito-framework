package com.viateur.testing.start.models.domains;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;
    private String message;
    private HttpStatus status;

    public ApiResponse(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}