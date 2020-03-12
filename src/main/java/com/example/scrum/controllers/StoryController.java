package com.example.scrum.controllers;

import com.example.scrum.service.StoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @DeleteMapping("/stories/{number}")
    public String deleteStory(HttpSession session, Model model, @PathVariable("number") Long number){

        Long projectId = (Long) session.getAttribute("projectId");
        storyService.delete(number, projectId);

        return "redirect:/backlog";
    }
}
