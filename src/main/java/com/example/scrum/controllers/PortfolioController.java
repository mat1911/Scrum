package com.example.scrum.controllers;

import com.example.scrum.dto.SprintPortfolioDto;
import com.example.scrum.security.UserDetailServiceImpl;
import com.example.scrum.service.ProjectService;
import com.example.scrum.service.SprintService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final ProjectService projectService;
    private final SprintService sprintService;

    @GetMapping("/portfolio")
    public String getPortfolioPage(Model model){
        model.addAttribute("projects", projectService.getAllArchivedUserProjects(UserDetailServiceImpl.getCurrentUserId()));
        return "portfolio/portfolio";
    }

    @GetMapping("/portfolio/projectDetails")
    public String getProjectDetails(Model model, @RequestParam Long id){

        List<SprintPortfolioDto> s = sprintService.getAllSprintPortfolioDto(id);
        model.addAttribute("project", projectService.getCurrentProjectAsDto(id));
        model.addAttribute("sprints", sprintService.getAllSprintPortfolioDto(id));

        return "portfolio/projectDetails";
    }

}
