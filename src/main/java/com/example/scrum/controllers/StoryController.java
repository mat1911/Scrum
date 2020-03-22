package com.example.scrum.controllers;

import com.example.scrum.dto.StoryBacklogDto;
import com.example.scrum.service.StoryService;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @ResponseBody
    @Synchronized
    @PostMapping("/assignUserToStory")
    public void assignUserToStory(@RequestParam("storyId") Long storyId){
        storyService.assignCurrentUserToStory(storyId);
    }

    @ResponseBody
    @Synchronized
    @PostMapping("/extractUserFromStory")
    @PreAuthorize("@accessManager.isAssignedToStory(#storyId)")
    public void extractUserFromStory(@RequestParam("storyId") Long storyId){
        storyService.extractUserFromStory(storyId);
    }

    @PostMapping("/updateStory/{id}")
    @PreAuthorize("@accessManager.isProductOwner(#session.getAttribute('projectId'))")
    public String updateStory(StoryBacklogDto storyDto, HttpSession session, @PathVariable("id") Long id){
        Long projectId = (Long) session.getAttribute("projectId");
        storyDto.setId(id);
        storyService.updateStory(projectId, storyDto);
        return "redirect:/backlog";
    }

    @PostMapping("/createStory")
    @PreAuthorize("@accessManager.isProductOwner(#session.getAttribute('projectId'))")
    public String createStory(StoryBacklogDto storyDto, HttpSession session) {
        Long projectId = (Long) session.getAttribute("projectId");
        storyService.createStory(projectId, storyDto);
        return "redirect:/backlog";
    }

    @PostMapping("/deleteStory/{id}")
    @PreAuthorize("@accessManager.isProductOwner(#session.getAttribute('projectId'))")
    public String deleteStory(HttpSession session, @PathVariable("id") Long id) {
        storyService.deleteStory(id);
        return "redirect:/backlog";
    }

    @PostMapping("/retireStory/{id}")
    @PreAuthorize("@accessManager.isProductOwner(#session.getAttribute('projectId'))")
    public String retireStory(HttpSession session, @PathVariable("id") Long id) {
        storyService.retireStory(id);
        return "redirect:/backlog";
    }
}
