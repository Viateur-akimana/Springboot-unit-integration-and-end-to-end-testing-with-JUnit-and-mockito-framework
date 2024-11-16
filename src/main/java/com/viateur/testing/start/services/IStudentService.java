package com.viateur.testing.start.services;

import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student createStudent(StudentDTO studentDTO);
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
}
