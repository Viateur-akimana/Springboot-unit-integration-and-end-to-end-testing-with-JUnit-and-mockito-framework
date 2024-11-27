package com.viateur.testing.start.services.impl;

import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.repositories.IStudentRepository;
import com.viateur.testing.start.utils.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private IStudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Utility method to create a mock Student entity
    private Student createMockStudent(Long id, String firstName, String lastName, String email, Integer age) {
        Student student = new Student();
        student.setId(id);
        student.setFirstname(firstName);
        student.setLastname(lastName);
        student.setEmail(email);
        student.setAge(age);
        return student;
    }

    // Utility method to create a mock StudentDTO
    private StudentDTO createMockStudentDTO(String firstName, String lastName, String email, Integer age) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname(firstName);
        studentDTO.setLastname(lastName);
        studentDTO.setEmail(email);
        studentDTO.setAge(age);
        return studentDTO;
    }

    @Test
    void shouldCreateStudent() {
        // Given
        StudentDTO studentDTO = createMockStudentDTO("John", "Doe", "john.doe@example.com", 20);
        Student student = createMockStudent(1L, "John", "Doe", "john.doe@example.com", 20);

        // Mock the repository save method to return the student
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        // Mock the StudentMapper to convert the DTO to the entity
        when(studentMapper.toEntity(studentDTO)).thenReturn(student);

        // When
        Student result = studentService.createStudent(studentDTO);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(20, result.getAge());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void shouldFetchAllStudents() {
        // Given
        Student student1 = createMockStudent(1L, "John", "Doe", "john.doe@example.com", 20);
        Student student2 = createMockStudent(2L, "Jane", "Doe", "jane.doe@example.com", 22);
        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        // When
        List<Student> students = studentService.getAllStudents();

        // Then
        assertEquals(2, students.size());
        assertEquals("John", students.get(0).getFirstname());
        assertEquals("Jane", students.get(1).getFirstname());
    }

    @Test
    void shouldGetStudentById() {
        // Given
        Long studentId = 1L;
        Student student = createMockStudent(studentId, "John", "Doe", "john.doe@example.com", 20);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // When
        Optional<Student> result = studentService.getStudentById(studentId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(studentId, result.get().getId());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenStudentNotFound() {
        // Given
        Long studentId = 99L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(studentId));
    }

    @Test
    void shouldUpdateStudent() {
        // Given
        Long studentId = 1L;
        StudentDTO studentDTO = createMockStudentDTO("John", "Updated", "john.updated@example.com", 22);
        Student existingStudent = createMockStudent(studentId, "John", "Doe", "john.doe@example.com", 20);

        // Mock repository to return the existing student
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Since updateEntity modifies the existing student in place, we don't need to mock a return value
        doNothing().when(studentMapper).updateEntity(existingStudent, studentDTO); // Mock that it does nothing

        // Mock save method to return the updated student
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);  // Save the updated student

        // When
        Student result = studentService.updateStudent(studentId, studentDTO);

        // Then
        assertEquals("Updated", result.getLastname());  // Check updated lastname
        assertEquals("john.updated@example.com", result.getEmail());  // Check updated email
        assertEquals("John", result.getFirstname());  // Check updated firstname
        assertEquals(22, result.getAge());  // Check updated age
        verify(studentRepository, times(1)).save(existingStudent);  // Verify that save was called once
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentStudent() {
        // Given
        Long studentId = 99L;
        StudentDTO studentDTO = createMockStudentDTO("John", "Updated", "john.updated@example.com", 22);

        // Mock repository to return empty for non-existent student
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(studentId, studentDTO));
    }

    @Test
    void shouldDeleteStudent() {
        // Given
        Long studentId = 1L;
        Student student = createMockStudent(studentId, "John", "Doe", "john.doe@example.com", 20);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Mock void method to do nothing
        doNothing().when(studentRepository).deleteById(studentId);

        // When
        studentService.deleteStudent(studentId);

        // Then
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentStudent() {
        // Given
        Long studentId = 99L;

        // Mock repository to return empty for non-existent student
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(studentId));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreatingStudentWithOddAge() {
        // Given
        StudentDTO studentDTO = createMockStudentDTO("John", "Doe", "john.doe@example.com", 21);

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> studentService.createStudent(studentDTO), "Age must be an even number");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUpdatingStudentWithOddAge() {
        // Given
        Long studentId = 1L;
        StudentDTO studentDTO = createMockStudentDTO("John", "Updated", "john.updated@example.com", 21);
        Student existingStudent = createMockStudent(studentId, "John", "Doe", "john.doe@example.com", 20);

        // Mock repository to return the existing student
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Since updateEntity modifies the existing student in place, we don't need to mock a return value
        doNothing().when(studentMapper).updateEntity(existingStudent, studentDTO); // Mock that it does nothing

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(studentId, studentDTO), "Age must be an even number");
    }
}
