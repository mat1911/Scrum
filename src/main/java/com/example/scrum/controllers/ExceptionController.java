package com.example.scrum.controllers;

import com.example.scrum.exceptions.ProjectNotSelectedException;

import com.example.scrum.exceptions.SprintNotFoundException;
import com.example.scrum.exceptions.StoryNotFoundException;
import com.example.scrum.exceptions.TokenException;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(TokenException.class)
    public String tokenExceptionHandler(TokenException e, Model model){

        model.addAttribute("messageHeader", "PROBLEM WITH TOKEN!");
        model.addAttribute("message", "Given token expired or is not correct. ");

        return "infoPages/informationPage";
    }

    @ExceptionHandler(StoryNotFoundException.class)
    public String storyNotFoundHandler(StoryNotFoundException e, Model model) {
        model.addAttribute("messageHeader", "STORY IS NOT FOUND!");
        model.addAttribute("message", e.getMessage());

        return "infoPages/informationPage";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDenied(AccessDeniedException e, Model model) {
        model.addAttribute("messageHeader", "ACCESS DENIED!");
        model.addAttribute("message", "You are trying to get into data you are not authorized to access!");

        return "infoPages/informationPage";
    }

}
