package com.viateur.testing.start.services.impl;

import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;
import com.viateur.testing.start.repositories.IStudentRepository;
import com.viateur.testing.start.services.IStudentService;
import com.viateur.testing.start.utils.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public Student createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.toEntity(studentDTO);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        StudentMapper.updateEntity(student, studentDTO);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
