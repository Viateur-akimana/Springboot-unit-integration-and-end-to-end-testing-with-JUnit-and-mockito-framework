package com.viateur.testing.start.utils;

import com.viateur.testing.start.models.domains.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseUtilTest {

    @Test
    void shouldReturnResponseEntityWithCorrectStatusAndPayload() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("Success", "Data loaded successfully", HttpStatus.OK);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals("Data loaded successfully", responseEntity.getBody().getData());
    }

    @Test
    void shouldReturnResponseEntityWithBadRequestStatusWhenStatusIsBadRequest() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("Invalid data", "Bad request data", HttpStatus.BAD_REQUEST);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Invalid data", responseEntity.getBody().getMessage());
        assertEquals("Bad request data", responseEntity.getBody().getData());
    }

    @Test
    void shouldReturnResponseEntityWithInternalServerErrorStatusWhenStatusIsInternalServerError() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("Server error", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Server error", responseEntity.getBody().getMessage());
        assertEquals("Something went wrong", responseEntity.getBody().getData());
    }

    @Test
    void shouldReturnResponseEntityWithNullDataWhenApiResponseHasNoData() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("Success", null, HttpStatus.OK);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

    @Test
    void shouldReturnResponseEntityWithCorrectStatusWhenApiResponseHasEmptyMessage() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("", "Empty message", HttpStatus.OK);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("", responseEntity.getBody().getMessage());
        assertEquals("Empty message", responseEntity.getBody().getData());
    }

    // Optional: Testing edge cases like empty ApiResponse
    @Test
    void shouldReturnResponseEntityWithEmptyResponse() {
        // Given
        ApiResponse<String> response = new ApiResponse<>("", null, HttpStatus.OK);

        // When
        ResponseEntity<ApiResponse<String>> responseEntity = ApiResponseUtil.toResponseEntity(response);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }
}
