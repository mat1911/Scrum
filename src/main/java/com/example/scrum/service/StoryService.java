package com.example.scrum.service;

import com.example.scrum.dto.StoriesDtoList;
import com.example.scrum.dto.StoryBacklogDto;
import com.example.scrum.dto.StoryKanbanDto;
import com.example.scrum.entity.Story;
import com.example.scrum.entity.User;
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
import java.util.Objects;
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

    public Story createStory(Long projectId, StoryBacklogDto storyDto) {

        Story story = storyMapper.storyBacklogToEntity(storyDto);
        story.setProject(projectRepository.findById(projectId).orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!")));
        story.setStatus(statusRepository.findByName("TO_DO").orElseThrow(() -> new ObjectNotFoundException("Status is not found!")));

        return storyRepository.save(story);
    }

    public Story deleteStory(Long storyId) {
        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));
        storyRepository.delete(story);
        return story;
    }

    public List<Story> findAllByProjectIdAndSprintId(Long projectId, Long sprintId) {
        return storyRepository.findAllByProject_IdAndSprint_Id(projectId, sprintId);
    }

    public List<StoryBacklogDto> findAllNotAssignedToSprint(Long projectId) {

        if (projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        return storyRepository
                .findAllByProject_Id(projectId)
                .stream()
                .filter(story -> Objects.isNull(story.getSprint()))
                .map(storyMapper::entityToStoryBacklogDto)
                .collect(Collectors.toList());
    }

    public Story extractUserFromStory(Long storyId) {

        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));

        story.setAssignedUser(null);
        return story;
    }

    public Story assignCurrentUserToStory(Long storyId) {

        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));

        if (story.getAssignedUser() != null) {
            return story;
        }

        User currentUser = userService.findById(UserDetailServiceImpl.getCurrentUserId());
        story.setAssignedUser(currentUser);
        return story;
    }

    public List<StoryKanbanDto> getStoriesFromCurrentSprint(Long projectId) {

        if (projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        return sprintRepository
                .findCurrentByProject_Id(projectId, LocalDate.now())
                .orElseThrow(() -> new SprintNotFoundException("Sprint with such project id does not exist!"))
                .getStories()
                .stream()
                .map(storyMapper::entityToStoryKanban)
                .collect(Collectors.toList());
    }

    public Story retireStory(Long storyId){
        Story story = storyRepository
                .findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));
        story.setSprint(null);
        return storyRepository.save(story);
    }

    public Story updateStory(Long projectId, StoryBacklogDto storyBacklogDto) {

        if (projectId == null) {
            throw new ProjectNotSelectedException("Project is not selected!");
        }

        Story story = storyMapper.storyBacklogToEntity(storyBacklogDto);
        story.setProject(projectRepository.findById(projectId).orElseThrow(() -> new ObjectNotFoundException("Project with a such id does not exist")));
        story.setStatus(statusRepository.findByName("TO_DO").orElseThrow(() -> new ObjectNotFoundException("Status with a such id does not exist")));

        if(storyBacklogDto.getSprintId() != null){
            story.setSprint(sprintRepository.findById(storyBacklogDto.getSprintId()).orElseThrow(() -> new SprintNotFoundException("Sprint with a such id is not found!")));
        }

        return storyRepository.save(story);
    }

    public void updateStoriesStatus(StoriesDtoList storiesDtoList) {

        storiesDtoList
                .getStories()
                .forEach(storyDto -> {

                    Story story = storyRepository
                            .findById(storyDto.getId())
                            .orElseThrow(() -> new StoryNotFoundException("Story with a such id is not found!"));

                    story.setStatus(statusRepository
                            .findByName(storyDto.getStatus().getName())
                            .orElseThrow(() -> new ObjectNotFoundException("Status with a such id does not exist")));

                    storyRepository.save(story);

                });

    }

}
