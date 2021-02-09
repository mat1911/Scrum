package com.example.scrum.controllers;

import com.example.scrum.dto.StoryBacklogDto;
import com.example.scrum.service.SprintService;
import com.example.scrum.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BacklogController {

    private final StoryService storyService;
    private final SprintService sprintService;

    @GetMapping("/backlog")
    public String getBacklog(Model model, HttpSession session) {
        Long projectId = (Long) session.getAttribute("projectId");
        model.addAttribute("stories", storyService.findAllNotAssignedToSprint(projectId));
        model.addAttribute("sprints", sprintService.findAllNotFinished(projectId));
        model.addAttribute("newStory", new StoryBacklogDto());
        return "backlog/backlog";
    }
}
