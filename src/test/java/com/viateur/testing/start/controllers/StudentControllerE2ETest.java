package com.viateur.testing.start.controllers;

import com.viateur.testing.start.models.domains.ApiResponse;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class StudentControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Test for creating a student
    @Test
    public void shouldCreateStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(25);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> request = new HttpEntity<>(studentDTO, headers);

        // Act
        ResponseEntity<ApiResponse<Student>> response = restTemplate.exchange(
                createURLWithPort("/api/v1/students"),
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Student created successfully");
        assertThat(response.getBody().getData().getFirstname()).isEqualTo("John");
    }

    // Test for getting all students
    @Test
    public void shouldGetAllStudents() {
        // Act
        ResponseEntity<ApiResponse<List<Student>>> response = restTemplate.exchange(
                createURLWithPort("/api/v1/students"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Students retrieved successfully");
        assertThat(response.getBody().getData()).isNotEmpty();
    }

    // Test for getting a student by ID
    @Test
    public void shouldGetStudentById() {
        // Arrange: Assuming student with ID 1 exists
        Long studentId = 1L;

        // Act
        ResponseEntity<ApiResponse<Student>> response = restTemplate.exchange(
                createURLWithPort("/api/v1/students/" + studentId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Student retrieved successfully");
    }

    // Test for updating a student
    @Test
    public void shouldUpdateStudent() {
        // Arrange: Assuming student with ID 1 exists
        Long studentId = 1L;
        StudentDTO updatedStudentDTO = new StudentDTO();
        updatedStudentDTO.setFirstname("Updated Name");
        updatedStudentDTO.setLastname("Updated LastName");
        updatedStudentDTO.setEmail("updated.email@example.com");
        updatedStudentDTO.setAge(26);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> request = new HttpEntity<>(updatedStudentDTO, headers);

        // Act
        ResponseEntity<ApiResponse<Student>> response = restTemplate.exchange(
                createURLWithPort("/api/v1/students/" + studentId),
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Student updated successfully");
    }

    // Test for deleting a student
    @Test
    public void shouldDeleteStudent() {
        // Arrange: Assuming student with ID 1 exists
        Long studentId = 1L;

        // Act
        ResponseEntity<ApiResponse<Void>> response = restTemplate.exchange(
                createURLWithPort("/api/v1/students/" + studentId),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody().getMessage()).isEqualTo("Student deleted successfully");
    }

    // Utility method to create URL with port
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
