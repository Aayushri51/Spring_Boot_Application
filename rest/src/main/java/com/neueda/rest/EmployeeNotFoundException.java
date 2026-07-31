package com.neueda.rest;

// CUSTOM EXCEPTION CLASS
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
