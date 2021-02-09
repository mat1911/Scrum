package com.example.scrum.controllers;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.dto.UserInvitationDto;
import com.example.scrum.entity.User;
import com.example.scrum.security.UserDetailServiceImpl;
import com.example.scrum.service.EmailService;
import com.example.scrum.service.ProjectService;
import com.example.scrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/projects")
    public String getProjects(Model model){

        Long userId = UserDetailServiceImpl.getCurrentUserId();
        List<ProjectDto> projects = projectService.getAllActiveUserProjects(userId);
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
    public String addNewProject(@ModelAttribute ProjectDto project){
        Long userId = UserDetailServiceImpl.getCurrentUserId();
        projectService.addNewProject(userId, project);
        return "redirect:/projects";
    }

    @PostMapping("/updateProject/{projectId}")
    @PreAuthorize("@accessManager.isProductOwner(#projectId)")
    public String updateProject(@ModelAttribute ProjectDto project, @PathVariable("projectId") Long projectId){
        projectService.updateProject(project, projectId);
        return "redirect:/projects";
    }

    @PostMapping("/selectProject/{projectId}")
    @ResponseBody
    public void selectProject(HttpSession session, @PathVariable("projectId") Long projectId){
        session.setAttribute("projectId", projectId);
        session.setAttribute("productOwnerId", projectService.findProjectOwner(projectId).getId());
      }

    @PostMapping("/assignUser")
    public String sendInvitationToUser(Model model, UserInvitationDto userInvitationDto, WebRequest request){

        User productOwner = userService.findById(UserDetailServiceImpl.getCurrentUserId());
        String appUrl = request.getContextPath();
        String token = userService.createVerificationToken(productOwner);

        emailService.sendInvitationToProject(userInvitationDto, productOwner, appUrl, token);

        model.addAttribute("messageHeader", "INVITATION HAS BEEN SENT");
        model.addAttribute("message", "Invitation to user has been sent. If user accept it, he will be added to project.");

        return "infoPages/informationPage";
    }

    @GetMapping("/acceptInvitation")
    @PreAuthorize("@accessManager.isProductOwner(#projectId)")
    public String assignUserToProject(@RequestParam("token") String token, @RequestParam("projectId") Long projectId){

        Long invitedUserId = UserDetailServiceImpl.getCurrentUserId();
        userService.deleteVerificationToken(token);
        projectService.addUserToProject(invitedUserId, projectId);

        return "redirect:/projects";
    }

    @GetMapping("/projectSettings/{projectId}")
    public String getProjectSettingsPage(HttpSession session, Model model, @PathVariable("projectId") Long projectId){

        session.setAttribute("projectId", projectId);
        session.setAttribute("productOwnerId", projectService.findProjectOwner(projectId).getId());
        model.addAttribute("currentProject", projectService.getCurrentProjectAsDto(projectId));
        model.addAttribute("assignedUsers", userService.findAllUsersAssignedToProject(projectId));
        return "projects/projectsSettings";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUserFromProject(HttpSession session, @PathVariable("id") Long id){

        Long projectId = (Long) session.getAttribute("projectId");
        projectService.deleteUserFromProject(id, projectId);

        return "redirect:/projectSettings/" + projectId;
    }

    @PostMapping("/archiveProject")
    @PreAuthorize("@accessManager.isProductOwner(#projectId)")
    public String archiveProject(HttpSession session){
        Long projectId = (Long) session.getAttribute("projectId");
        projectService.setProjectStatus(projectId, true);
        return "redirect:/projects";
    }

    @PostMapping("/recoverProject/{id}")
    @PreAuthorize("@accessManager.isProductOwner(#projectId)")
    public String recoverProject(@PathVariable("id") Long id){
        projectService.setProjectStatus(id, false);
        return "redirect:/projects";
    }

}
