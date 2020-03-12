package com.example.scrum.exceptions;

public class StoryNotFoundException extends RuntimeException {
    public StoryNotFoundException(String message) {
        super(message);
    }
}
