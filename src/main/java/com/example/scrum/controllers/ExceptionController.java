package com.example.scrum.controllers;

import com.example.scrum.exceptions.ProjectNotSelectedException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ProjectNotSelectedException.class})
    public String appExceptionHandler(ProjectNotSelectedException e) {
        return "redirect:/projects";
    }

}
