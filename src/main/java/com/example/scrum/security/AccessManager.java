package com.example.scrum.security;

import com.example.scrum.exceptions.StoryNotFoundException;
import com.example.scrum.repository.StoryRepository;
import com.example.scrum.service.ProjectService;
import com.example.scrum.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessManager {

    private final ProjectService projectService;
    private final StoryRepository storyRepository;

    public boolean isProductOwner(Long projectId){
        return projectService
                .findProjectOwner(projectId)
                .getId()
                .equals(UserDetailServiceImpl.getCurrentUserId());
    }

    public boolean isAssignedToStory(Long storyId){
        return storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"))
                .getId()
                .equals(UserDetailServiceImpl.getCurrentUserId());
    }
}
