package com.viateur.testing.start.models.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Firstname is required")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "Lastname is required")
    @Column(name = "lastname")
    private String lastname;

    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true)
    private String email;

    @Min(value = 16, message = "Age must be at least 16")
    @Column(name = "age")
    private Integer age;

}