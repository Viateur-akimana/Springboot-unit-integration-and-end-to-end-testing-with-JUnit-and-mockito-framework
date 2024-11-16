package com.viateur.testing.start.utils;

import com.viateur.testing.start.models.domains.Student;
import com.viateur.testing.start.models.dtos.StudentDTO;

public class StudentMapper {
    public static Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        return student;
    }

    public static void updateEntity(Student student, StudentDTO dto) {
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
    }
}
