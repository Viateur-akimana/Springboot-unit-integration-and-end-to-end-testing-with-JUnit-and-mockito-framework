package com.viateur.testing.start.exceptions;

import com.viateur.testing.start.models.domains.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

public class GlobalExceptionHandler {

    //Student not found exception
    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleStudentNotFoundException(StudentNotFoundException ex){
        ApiResponse<Void> apiResponse=new ApiResponse<>(null, ex.getMessage(), HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);

    }
    //duplicate students
    @ExceptionHandler(value = DuplicateStudentException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateStudent(DuplicateStudentException ex){
        ApiResponse<Void> apiResponse= new ApiResponse<>(null, ex.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    //handling constraint validation exception
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex){
        ApiResponse<Void> apiResponse= new ApiResponse<>(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    //generic exception handling
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericExceptions(Exception ex){
        ApiResponse<Void> apiResponse=new ApiResponse<>(null, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
