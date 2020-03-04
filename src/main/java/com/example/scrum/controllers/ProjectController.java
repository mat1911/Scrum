package com.example.scrum.controllers;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.security.UserDetailsImpl;
import com.example.scrum.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public String getProjects(Model model){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();

        List<ProjectDto> projects = projectService.getAllUserProjects(userId);
        model.addAttribute("projects", projects);

        return "projects/userProjects";
    }

    @GetMapping("/newProject")
    public String getNewProjectPage(Model model){

        model.addAttribute("project", new ProjectDto());

        return "projects/newProject";
    }

    @PostMapping("/newProject")
    public String addNewProject(ProjectDto project, Model model){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();

        projectService.addNewProject(userId, project);

        return "redirect:/projects";
    }


}
