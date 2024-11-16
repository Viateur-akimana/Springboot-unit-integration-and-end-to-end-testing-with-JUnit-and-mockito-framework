package com.viateur.testing.start.repositories;

import com.viateur.testing.start.models.domains.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class IStudentRepositoryTest {

    @Autowired
    private IStudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        // Set up a Student entity with sample data
        student = new Student();
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(25);
        studentRepository.save(student);
    }

    @Test
    void shouldFindStudentByEmail() {
        Optional<Student> foundStudent = studentRepository.findByEmail("john.doe@example.com");
        assertTrue(foundStudent.isPresent(), "Student should be found by email");
        assertThat(foundStudent.get().getFirstname()).isEqualTo("John");
        assertThat(foundStudent.get().getLastname()).isEqualTo("Doe");
    }

    @Test
    void shouldNotFindStudentByNonExistentEmail() {
        Optional<Student> foundStudent = studentRepository.findByEmail("nonexistent.email@example.com");
        assertTrue(foundStudent.isEmpty(), "No student should be found for a non-existent email");
    }

    @Test
    void shouldExistByEmail() {
        boolean exists = studentRepository.existsByEmail("john.doe@example.com");
        assertTrue(exists, "Student should exist by email");
    }

    @Test
    void shouldNotExistByEmail() {
        boolean exists = studentRepository.existsByEmail("nonexistent.email@example.com");
        assertFalse(exists, "Student should not exist by a non-existent email");
    }
}
