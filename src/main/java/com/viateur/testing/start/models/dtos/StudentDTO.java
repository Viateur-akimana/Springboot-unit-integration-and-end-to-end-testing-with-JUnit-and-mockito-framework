package com.viateur.testing.start.models.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Data
public class StudentDTO {
    @NotEmpty(message = "Firstname is required")
    private String firstname;

    @NotEmpty(message = "Lastname is required")
    private String lastname;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 16, message = "Age must be at least 16")
    private Integer age;
}