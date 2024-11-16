package com.viateur.testing.start.utils;

import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    @Test
    void shouldMapAllFieldsFromDtoToEntity() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setAge(25);

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertEquals("John", student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals(25, student.getAge());
    }

    @Test
    void shouldMapFirstnameToNullWhenNotProvidedInDto() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setFirstname(null);
        dto.setLastname("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setAge(25);

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertNull(student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals(25, student.getAge());
    }

    @Test
    void shouldMapLastnameToNullWhenNotProvidedInDto() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setFirstname("John");
        dto.setLastname(null);
        dto.setEmail("john.doe@example.com");
        dto.setAge(25);

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertEquals("John", student.getFirstname());
        assertNull(student.getLastname());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals(25, student.getAge());
    }

    @Test
    void shouldMapEmailToNullWhenNotProvidedInDto() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail(null);
        dto.setAge(25);

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertEquals("John", student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertNull(student.getEmail());
        assertEquals(25, student.getAge());
    }

    @Test
    void shouldMapAgeToZeroWhenNotProvidedInDto() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setAge(null);

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertEquals("John", student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertEquals("john.doe@example.com", student.getEmail());
        assertNull(student.getAge()); // Assuming age is Integer and can be null
    }

    @Test
    void shouldMapAllFieldsToNullWhenDtoIsEmpty() {
        // Given
        StudentDTO dto = new StudentDTO();

        // When
        Student student = StudentMapper.toEntity(dto);

        // Then
        assertNotNull(student);
        assertNull(student.getFirstname());
        assertNull(student.getLastname());
        assertNull(student.getEmail());
        assertNull(student.getAge());
    }

    @Test
    void shouldUpdateStudentFieldsWhenDtoIsProvided() {
        // Given
        Student student = new Student();
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(25);

        StudentDTO dto = new StudentDTO();
        dto.setFirstname("Jane");
        dto.setLastname("Smith");
        dto.setEmail("jane.smith@example.com");
        dto.setAge(28);

        // When
        StudentMapper.updateEntity(student, dto);

        // Then
        assertNotNull(student);
        assertEquals("Jane", student.getFirstname());
        assertEquals("Smith", student.getLastname());
        assertEquals("jane.smith@example.com", student.getEmail());
        assertEquals(28, student.getAge());
    }
}
