package com.viateur.testing.start.utils;

import com.viateur.testing.start.models.domains.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(ApiResponse<T> response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}
