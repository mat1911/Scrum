package com.example.scrum.controllers;

import com.example.scrum.dto.SprintBacklogDto;
import com.example.scrum.dto.StoryBacklogDto;
import com.example.scrum.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @GetMapping("/sprints")
    public String getSprintsPage(Model model, HttpSession session){
        Long projectId = (Long) session.getAttribute("projectId");
        model.addAttribute("sprints", sprintService.findAllByProjectId(projectId));
        model.addAttribute("newStory", new StoryBacklogDto());
        model.addAttribute("newSprint", new SprintBacklogDto());
        model.addAttribute("errors", new HashMap<>());
        return "sprints/sprints";
    }

    @PostMapping("/createSprint")
    public String createSprint(SprintBacklogDto sprintBacklogDto, HttpSession session){

        Long projectId = (Long) session.getAttribute("projectId");
        sprintService.createNewSprint(projectId, sprintBacklogDto);
        return "redirect:/sprints";
    }

    @PostMapping("/updateSprint/{id}")
    public String updateSprint(SprintBacklogDto sprintBacklogDto, HttpSession session, @PathVariable("id") Long id){
        Long projectId = (Long) session.getAttribute("projectId");
        sprintService.updateSprint(projectId, id,sprintBacklogDto);
        return "redirect:/sprints";
    }

    @PostMapping("/deleteSprint/{id}")
    public String deleteSprint(@PathVariable("id") Long id){
        sprintService.deleteSprint(id);
        return "redirect:/sprints";
    }

}
