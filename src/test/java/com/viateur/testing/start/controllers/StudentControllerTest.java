package com.viateur.testing.start.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.services.IStudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService studentService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateStudent() throws Exception {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(20);

        Student student = new Student();
        student.setId(1L);
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(20);

        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(student);

        // Act and Assert
        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.firstname").value("John"))
                .andExpect(jsonPath("$.data.lastname").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.age").value(20))
                .andExpect(jsonPath("$.message").value("Student created successfully"));
    }

    @Test
    void shouldGetAllStudents() throws Exception {
        // Arrange
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstname("John");
        student1.setLastname("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setAge(20);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstname("Jane");
        student2.setLastname("Smith");
        student2.setEmail("jane.smith@example.com");
        student2.setAge(22);

        when(studentService.getAllStudents()).thenReturn(List.of(student1, student2));

        // Act and Assert
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].firstname").value("John"))
                .andExpect(jsonPath("$.data[1].firstname").value("Jane"))
                .andExpect(jsonPath("$.message").value("Students retrieved successfully"));
    }

    @Test
    void shouldGetStudentById() throws Exception {
        // Arrange
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(20);

        when(studentService.getStudentById(studentId)).thenReturn(java.util.Optional.of(student));

        // Act and Assert
        mockMvc.perform(get("/api/v1/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstname").value("John"))
                .andExpect(jsonPath("$.message").value("Student retrieved successfully"));
    }

    @Test
    void shouldReturn404WhenStudentNotFound() throws Exception {
        // Arrange
        Long studentId = 999L;
        when(studentService.getStudentById(studentId)).thenThrow(new EntityNotFoundException("Student not found"));

        // Act and Assert
        mockMvc.perform(get("/api/v1/students/{id}", studentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        // Arrange
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(21);

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstname("John");
        updatedStudent.setLastname("Doe");
        updatedStudent.setEmail("john.doe@example.com");
        updatedStudent.setAge(21);

        when(studentService.updateStudent(anyLong(), any(StudentDTO.class))).thenReturn(updatedStudent);

        // Act and Assert
        mockMvc.perform(put("/api/v1/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstname").value("John"))
                .andExpect(jsonPath("$.data.age").value(21))
                .andExpect(jsonPath("$.message").value("Student updated successfully"));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        // Arrange
        Long studentId = 1L;

        // Mocking the void method to do nothing (indicating successful deletion)
        doNothing().when(studentService).deleteStudent(studentId);

        // Act and Assert
        mockMvc.perform(delete("/api/v1/students/{id}", studentId))
                .andExpect(status().isNoContent())  // Expect 204 No Content for successful deletion
                .andExpect(jsonPath("$.message").value("Student deleted successfully"));
    }

}
