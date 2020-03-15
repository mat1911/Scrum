package com.example.scrum.controllers;

import com.example.scrum.security.UserDetailServiceImpl;
import com.example.scrum.service.ProjectService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final ProjectService projectService;

    @GetMapping("/portfolio")
    public String getPortfolioPage(Model model){
        model.addAttribute("projects", projectService.getAllArchivedUserProjects(UserDetailServiceImpl.getCurrentUserId()));
        return "portfolio/portfolio";
    }

}
