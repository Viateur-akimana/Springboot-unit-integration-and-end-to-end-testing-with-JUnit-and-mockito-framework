package com.viateur.testing.start.controllers;

import com.viateur.testing.start.models.domains.ApiResponse;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.services.IStudentService;
import com.viateur.testing.start.utils.ApiResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * Endpoint to create a new student. Returns the created student in the response.
     * */
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentDTO request) {
        // Create student and save it
        Student student = studentService.createStudent(request);
        ApiResponse<Student> response = new ApiResponse<>(student, "Student created successfully", HttpStatus.CREATED);
        log.info("Student with ID {} created successfully", student.getId());
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to fetch all students from the database.
     * */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        // Get all students
        List<Student> students = studentService.getAllStudents();
        ApiResponse<List<Student>> response = new ApiResponse<>(students, "Students retrieved successfully", HttpStatus.OK);
        log.info("Fetched all students from the database.");
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to fetch a student by its ID.
     * Throws an exception if student is not found.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        // Get student by ID
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        ApiResponse<Student> response = new ApiResponse<>(student, "Student retrieved successfully", HttpStatus.OK);
        log.info("Fetched student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to update a student's information.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO request) {
        // Update student information
        Student student = studentService.updateStudent(id, request);
        ApiResponse<Student> response = new ApiResponse<>(student, "Student updated successfully", HttpStatus.OK);
        log.info("Updated student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }

    /**
     * Endpoint to delete a student by its ID.
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        // Delete student
        studentService.deleteStudent(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Student deleted successfully", HttpStatus.NO_CONTENT);
        log.info("Deleted student with ID: {}", id);
        return ApiResponseUtil.toResponseEntity(response);
    }
}
