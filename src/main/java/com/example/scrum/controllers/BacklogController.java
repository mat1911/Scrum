package com.example.scrum.controllers;

import com.example.scrum.dto.StoryDto;
import com.example.scrum.service.StoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class BacklogController {

    private final StoryService storyService;

    @GetMapping("/backlog")
    public String getBacklog(Model model, HttpSession session) {
        Long projectId = (Long) session.getAttribute("projectId");
        List<StoryDto> stories = storyService.getStoriesByProjectId(projectId);
        model.addAttribute("stories", stories);
        return "backlog";
    }

    /*@DeleteMapping("/backlog/deleteStory")
    public String deleteStory(HttpSession session, Model model, @RequestParam("number") Long number){
        Long projectId = (Long) session.getAttribute("projectId");
        storyService.delete(number, projectId);
        return "redirect:/backlog";
    }*/
}
