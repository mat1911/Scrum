package com.example.scrum.controllers;

import com.example.scrum.service.StoryService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class StoriesController {

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
    public void extractUserFromStory(@RequestParam("storyId") Long storyId){
        storyService.extractUserFromStory(storyId);
    }

}
