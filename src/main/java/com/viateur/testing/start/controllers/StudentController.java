package com.viateur.testing.start.controllers;

import com.viateur.testing.start.models.domains.ApiResponse;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.services.IStudentService;
import com.viateur.testing.start.utils.ApiResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentDTO request) {
        Student student = studentService.createStudent(request);
        ApiResponse<Student> response = new ApiResponse<>(student, "Student created successfully", HttpStatus.CREATED);
        return ApiResponseUtil.toResponseEntity(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        ApiResponse<List<Student>> response = new ApiResponse<>(students, "Students retrieved successfully", HttpStatus.OK);
        return ApiResponseUtil.toResponseEntity(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        ApiResponse<Student> response = new ApiResponse<>(student, "Student retrieved successfully", HttpStatus.OK);
        return ApiResponseUtil.toResponseEntity(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO request) {
        Student student = studentService.updateStudent(id, request);
        ApiResponse<Student> response = new ApiResponse<>(student, "Student updated successfully", HttpStatus.OK);
        return ApiResponseUtil.toResponseEntity(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Student deleted successfully", HttpStatus.NO_CONTENT);
        return ApiResponseUtil.toResponseEntity(response);
    }
}
