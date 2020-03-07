package com.example.scrum.service;

import com.example.scrum.dto.StoriesDtoList;
import com.example.scrum.dto.StoryDto;
import com.example.scrum.exceptions.ObjectNotFoundException;
import com.example.scrum.exceptions.ProjectNotSelectedException;
import com.example.scrum.exceptions.SprintNotFoundException;
import com.example.scrum.mappers.StoryMapper;
import com.example.scrum.repository.ProjectRepository;
import com.example.scrum.repository.SprintRepository;
import com.example.scrum.repository.StatusRepository;
import com.example.scrum.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final SprintRepository sprintRepository;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;
    private final StoryMapper storyMapper;

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
