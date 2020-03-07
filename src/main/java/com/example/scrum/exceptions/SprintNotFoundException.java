package com.example.scrum.exceptions;

public class SprintNotFoundException extends RuntimeException {
    public SprintNotFoundException(String message) {
        super(message);
    }
}
