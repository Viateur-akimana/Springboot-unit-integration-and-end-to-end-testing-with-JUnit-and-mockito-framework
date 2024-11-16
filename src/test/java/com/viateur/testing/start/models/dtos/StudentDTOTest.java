package com.viateur.testing.start.models.dtos;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    // **Test 1: Should pass validation with valid student data**
    @Test
    void shouldPassValidationWithValidStudentData() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(20);

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertTrue(violations.isEmpty(), "DTO with valid data should pass validation");
    }

    // **Test 2: Should fail validation with missing first name**
    @Test
    void shouldFailValidationWithMissingFirstName() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(20);

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with missing firstname should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for missing firstname");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("firstname", violation.getPropertyPath().toString(), "Invalid field should be firstname");
        assertEquals("Firstname is required", violation.getMessage(), "Missing firstname should trigger 'Firstname is required' message");
    }

    // **Test 3: Should fail validation with missing last name**
    @Test
    void shouldFailValidationWithMissingLastName() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(20);

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with missing lastname should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for missing lastname");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("lastname", violation.getPropertyPath().toString(), "Invalid field should be lastname");
        assertEquals("Lastname is required", violation.getMessage(), "Missing lastname should trigger 'Lastname is required' message");
    }

    // **Test 4: Should fail validation with invalid email format**
    @Test
    void shouldFailValidationWithInvalidEmailFormat() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("invalid-email");
        studentDTO.setAge(20);

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with invalid email should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for invalid email format");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString(), "Invalid field should be email");
        assertEquals("Invalid email format", violation.getMessage(), "Invalid email should trigger 'Invalid email format' message");
    }

    // **Test 5: Should fail validation with age less than 16**
    @Test
    void shouldFailValidationWithAgeLessThan16() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(15);  // Age is less than 16

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with age less than 16 should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for age less than 16");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("age", violation.getPropertyPath().toString(), "Invalid field should be age");
        assertEquals("Age must be at least 16", violation.getMessage(), "Age less than 16 should trigger 'Age must be at least 16' message");
    }

    // **Test 6: Should fail validation with empty email**
    @Test
    void shouldFailValidationWithEmptyEmail() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("");  // Empty email
        studentDTO.setAge(20);

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with empty email should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for empty email");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString(), "Invalid field should be email");
        assertEquals("Invalid email format", violation.getMessage(), "Empty email should trigger 'Invalid email format' message");
    }

    // **Test 7: Should fail validation with null age**
    @Test
    void shouldFailValidationWithNullAge() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setAge(null);  // Null age

        // Act
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);

        // Assert
        assertFalse(violations.isEmpty(), "DTO with null age should fail validation");
        assertEquals(1, violations.size(), "There should be 1 violation for null age");
        ConstraintViolation<StudentDTO> violation = violations.iterator().next();
        assertEquals("age", violation.getPropertyPath().toString(), "Invalid field should be age");
        assertEquals("Age must be at least 16", violation.getMessage(), "Null age should trigger 'Age must be at least 16' message");
    }
}
