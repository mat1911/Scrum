package com.example.scrum.controllers;

import com.example.scrum.dto.StoriesDtoList;
import com.example.scrum.security.UserDetailServiceImpl;
import com.example.scrum.service.ProjectService;
import com.example.scrum.service.StoryService;
import com.example.scrum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class KanbanController {

    private final StoryService storyService;
    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping("/kanbanBoard")
    @Synchronized
    public String kanban(Model model, HttpSession session){

        Long projectId = (Long) session.getAttribute("projectId");
        model.addAttribute("statuses", projectService.findAllStatuses());
        model.addAttribute("stories", storyService.getStoriesFromCurrentSprint(projectId));
        model.addAttribute("assignedUser", userService.findAssignedUserById(UserDetailServiceImpl.getCurrentUserId()));
        return "/kanban/kanbanBoard";
    }

    @PostMapping("/updateStories")
    @ResponseBody
    @Synchronized
    public void updateStories(@RequestBody StoriesDtoList stories){
        storyService.updateStoriesStatus(stories);
    }

}
