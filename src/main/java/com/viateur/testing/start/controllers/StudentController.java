package com.viateur.testing.start.controllers;

import com.viateur.testing.start.models.domains.ApiResponse;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.services.IStudentService;
import com.viateur.testing.start.services.MessageService;
import com.viateur.testing.start.utils.ApiResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final IStudentService studentService;
    @Autowired
    private MessageService messageService;

    /**
     * Endpoint to create a new student. Returns the created student in the response.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentDTO request,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        // Create student and save it
        Student student = studentService.createStudent(request);

        // Fetching localized message using MessageSource
        String message = messageService.getMessage("student.created");

        ApiResponse<Student> response = new ApiResponse<>(student, message, HttpStatus.CREATED);
        log.info("Student with ID {} created successfully", student.getId());
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to fetch all students from the database.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        // Get all students
        List<Student> students = studentService.getAllStudents();

        // Fetching localized message using MessageSource
        String message = messageService.getMessage("students.retrieved");

        ApiResponse<List<Student>> response = new ApiResponse<>(students, message, HttpStatus.OK);
        log.info("Fetched all students from the database.");
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to fetch a student by its ID.
     * Throws an exception if student is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id,
                                                               @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        // Get student by ID
        Student student = studentService.getStudentById(id).orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("student.not.found")));

        // Fetching localized message using MessageSource
        String message = messageService.getMessage("student.retrieved");

        ApiResponse<Student> response = new ApiResponse<>(student, message, HttpStatus.OK);
        log.info("Fetched student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to update a student's information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id,
                                                              @Valid @RequestBody StudentDTO request,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        // Update student information
        Student student = studentService.updateStudent(id, request);

        // Fetching localized message using MessageSource
        String message = messageService.getMessage("student.updated");

        ApiResponse<Student> response = new ApiResponse<>(student, message, HttpStatus.OK);
        log.info("Updated student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to delete a student by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id,
                                                           @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        // Delete student
        studentService.deleteStudent(id);

        // Fetching localized message using MessageSource
        String message = messageService.getMessage("student.deleted");

        ApiResponse<Void> response = new ApiResponse<>(null, message, HttpStatus.NO_CONTENT);
        log.info("Deleted student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }
}
