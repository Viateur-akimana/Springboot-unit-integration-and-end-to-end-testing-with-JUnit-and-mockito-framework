package com.viateur.testing.start.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public  StudentNotFoundException(String message){
        super(message);
    }
}