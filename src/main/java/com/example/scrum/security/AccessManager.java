package com.example.scrum.security;

import com.example.scrum.service.ProjectService;
import com.example.scrum.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessManager {

    private final ProjectService projectService;
    private final StoryService storyService;

    public boolean isProductOwner(Long projectId){
        return projectService
                .findProjectOwner(projectId)
                .getId()
                .equals(UserDetailServiceImpl.getCurrentUserId());
    }

    public boolean isAssignedToStory(Long storyId){
        return storyService
                .findById(storyId)
                .getAssignedUser()
                .getId()
                .equals(UserDetailServiceImpl.getCurrentUserId());
    }
}
