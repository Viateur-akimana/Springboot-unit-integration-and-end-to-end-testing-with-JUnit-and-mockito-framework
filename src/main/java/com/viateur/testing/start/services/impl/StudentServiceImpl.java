package com.viateur.testing.start.services.impl;

import com.viateur.testing.start.exceptions.StudentNotFoundException;
import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.repositories.IStudentRepository;
import com.viateur.testing.start.services.IStudentService;
import com.viateur.testing.start.services.MessageService;
import com.viateur.testing.start.utils.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private IStudentRepository studentRepository;

    /**
     * Create a new student and save it in the database.
     * */
    @Override
    public Student createStudent(StudentDTO studentDTO) {
        // Convert DTO to entity
        Student student = StudentMapper.toEntity(studentDTO);
        // Save student and return the saved entity
        log.info("Creating a new student with ID: {}", student.getId());
        return studentRepository.save(student);
    }

    /**
     * Fetch all students from the database.
     * */
    @Override
    public List<Student> getAllStudents() {
        log.info("Fetching all students from the database.");
        return studentRepository.findAll();
    }

    /**
     * Get a student by its ID.
     * If the student is not found, throw an exception.
     * */
    @Override
    public Optional<Student> getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        return studentRepository.findById(id);
    }

    /**
     * Update an existing student. If not found, throw an exception.
     * */
    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        // Find student by ID
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(messageService.getMessage("student.not.found")));
        // Update student with data from DTO
        StudentMapper.updateEntity(student, studentDTO);
        log.info("Updating student with ID: {}", id);
        // Save and return the updated student
        return studentRepository.save(student);
    }

    /**
     * Delete a student by its ID.
     * */
    @Override
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        studentRepository.deleteById(id);
    }
}
