package com.viateur.testing.start.models.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    private ApiResponse<String> apiResponse;

    @BeforeEach
    void setUp() {
        // Set up the test case
        apiResponse = new ApiResponse<>("Test data", "Success", HttpStatus.OK);
    }

    @Test
    void testConstructor_initialization() {
        assertNotNull(apiResponse);
        assertEquals("Test data", apiResponse.getData());
        assertEquals("Success", apiResponse.getMessage());
        assertEquals(HttpStatus.OK, apiResponse.getStatus());
    }

    @Test
    void testTimestamp_isNotNull() {
        assertNotNull(apiResponse.getTimestamp());
    }

    @Test
    void testTimestamp_isCurrentTime() {
        // Allow a small margin of error (e.g., 100 ms) since LocalDateTime.now() might vary slightly.
        LocalDateTime now = LocalDateTime.now();
        assertTrue(apiResponse.getTimestamp().isBefore(now.plusSeconds(1)) && apiResponse.getTimestamp().isAfter(now.minusSeconds(1)));
    }

    @Test
    void testSetters_andGetters() {
        apiResponse.setData("New data");
        apiResponse.setMessage("Updated message");
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);

        assertEquals("New data", apiResponse.getData());
        assertEquals("Updated message", apiResponse.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatus());
    }

    @Test
    void testApiResponse_EmptyConstructor() {
        ApiResponse<String> emptyResponse = new ApiResponse<>(null, null, null);
        assertNull(emptyResponse.getData());
        assertNull(emptyResponse.getMessage());
        assertNull(emptyResponse.getStatus());
    }
}
