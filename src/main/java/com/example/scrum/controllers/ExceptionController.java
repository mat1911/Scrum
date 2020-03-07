package com.example.scrum.controllers;

import com.example.scrum.exceptions.ProjectNotSelectedException;

import com.example.scrum.exceptions.SprintNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ProjectNotSelectedException.class})
    public String projectNotSelectedHandler(ProjectNotSelectedException e) {
        return "redirect:/projects";
    }

    @ExceptionHandler(SprintNotFoundException.class)
    public String sprintNotFoundHandler(SprintNotFoundException e, Model model){

        model.addAttribute("messageHeader", "SPRINT IS NOT FOUND!");
        model.addAttribute("message", "No sprint is currently being processed! To see this page " +
                "create new sprint which contain today date.");

        return "infoPages/informationPage";
    }

}
