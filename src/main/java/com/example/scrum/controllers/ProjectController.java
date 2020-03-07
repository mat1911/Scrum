package com.example.scrum.controllers;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.dto.UserInvitationDto;
import com.example.scrum.entity.Project;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.security.UserDetailsImpl;
import com.example.scrum.service.EmailService;
import com.example.scrum.service.ProjectService;
import com.example.scrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/projects")
    public String getProjects(Model model){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();

        List<ProjectDto> projects = projectService.getAllUserProjects(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("userInvitation", new UserInvitationDto());

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

    @PostMapping("/assignUser")
    public String sendInvitationToUser(Model model, UserInvitationDto userInvitationDto, WebRequest request){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User productOwner = userService.findById(userDetails.getUserId());

        String appUrl = request.getContextPath();
        String token = userService.createVerificationToken(productOwner);

        emailService.sendInvitationToProject(userInvitationDto, productOwner, appUrl, token);

        model.addAttribute("messageHeader", "INVITATION HAS BEEN SENT");
        model.addAttribute("message", "Invitation to user has been sent. If user accept it, he will be added to project.");

        return "infoPages/informationPage";
    }

    @GetMapping("/acceptInvitation")
    public String assignUserToProject(@RequestParam("token") String token, @RequestParam("projectId") Long projectId){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long invitedUserId = userDetails.getUserId();

        //userService.getVerificationToken(token);
        userService.deleteVerificationToken(token);
        projectService.addUserToProject(invitedUserId, projectId);

        return "redirect:/projects";
    }

}
