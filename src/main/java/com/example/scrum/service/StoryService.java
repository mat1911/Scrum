package com.example.scrum.service;

import com.example.scrum.dto.StoriesDtoList;
import com.example.scrum.dto.StoryDto;
import com.example.scrum.entity.Story;
import com.example.scrum.exceptions.ObjectNotFoundException;
import com.example.scrum.exceptions.ProjectNotSelectedException;
import com.example.scrum.exceptions.SprintNotFoundException;
import com.example.scrum.exceptions.StoryNotFoundException;
import com.example.scrum.mappers.StoryMapper;
import com.example.scrum.repository.ProjectRepository;
import com.example.scrum.repository.SprintRepository;
import com.example.scrum.repository.StatusRepository;
import com.example.scrum.repository.StoryRepository;
import com.example.scrum.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final SprintRepository sprintRepository;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final StoryMapper storyMapper;

    public void delete(Long number, Long projectId) {
        Optional<Story> story = storyRepository
                .findAllByProject_Id(projectId)
                .stream()
                .filter(str -> str.getNumber().equals(number))
                .findFirst();
        if(story.isPresent())
            storyRepository.delete(story.get());
        else
            throw new StoryNotFoundException("There is no story associated with such story number and project id!");
    }

    public List<StoryDto> getStoriesByProjectId(Long projectId) {
        if(projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        return storyRepository
                .findAllByProject_Id(projectId)
                .stream().map(storyMapper::toDto)
                .collect(Collectors.toList());
    }

    public Story extractUserFromStory(Long storyId){

        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));

        story.setAssignedUser(null);
        return story;
    }

    public Story assignCurrentUserToStory(Long storyId){

        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));

        if(story.getAssignedUser() != null){
            return story;
        }

        User currentUser = userService.findById(UserDetailServiceImpl.getCurrentUserId());
        story.setAssignedUser(currentUser);
        return story;
    }

    public List<StoryDto> getStoriesFromCurrentSprint(Long projectId) {

        if (projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        return sprintRepository
                .findCurrentByProject_Id(projectId, LocalDate.now())
                .orElseThrow(() -> new SprintNotFoundException("Sprint with such project id does not exist!"))
                .getStories()
                .stream()
                .map(storyMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateStories(StoriesDtoList storiesDtoList, Long projectId, Long sprintId) {

        if (projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        if (sprintId == null) {
            throw new SprintNotFoundException("Sprint is not found!");
        }

        storiesDtoList
                .getStories()
                .stream()
                .map(storyMapper::toEntity)
                .forEach(story -> {

                    story.setProject(projectRepository
                            .findById(projectId)
                            .orElseThrow(() -> new ObjectNotFoundException("Project with a such id does not exist")));

                    story.setSprint(sprintRepository
                            .findById(sprintId)
                            .orElseThrow(() -> new ObjectNotFoundException("Sprint with a such id does not exist")));

                    story.setStatus(statusRepository
                            .findByName(story.getStatus().getName())
                            .orElseThrow(() -> new ObjectNotFoundException("Status with a such id does not exist")));

                    storyRepository.save(story);

                });

    }

}
