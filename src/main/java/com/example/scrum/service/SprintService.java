package com.example.scrum.service;

import com.example.scrum.dto.SprintBacklogDto;
import com.example.scrum.dto.SprintPortfolioDto;
import com.example.scrum.entity.Sprint;
import com.example.scrum.exceptions.ObjectNotFoundException;
import com.example.scrum.exceptions.ProjectNotSelectedException;
import com.example.scrum.mappers.SprintMapper;
import com.example.scrum.repository.ProjectRepository;
import com.example.scrum.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final SprintMapper sprintMapper;

    public List<SprintBacklogDto> findAllNotFinished(Long projectId){
        List<Sprint> sprints = sprintRepository
                .findAllNotFinished(projectId, LocalDate.now());

        return sprintMapper.sprintToSprintBacklogDto(sprints);
    }

    public Sprint createNewSprint(Long projectId, SprintBacklogDto sprintBacklogDto){

        if(projectId == null){
            throw new ProjectNotSelectedException("Project is not selected!");
        }
        Sprint sprint = sprintMapper.sprintBacklogToSprint(sprintBacklogDto);
        sprint.setProject(projectRepository.findById(projectId).orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!")));
        return sprintRepository.save(sprint);
    }

    public List<SprintBacklogDto> findAllByProjectId(Long projectId){
        List<Sprint> sprints = sprintRepository
                .findAllByProject_IdOrderByIdAsc(projectId);

        return sprintMapper.sprintToSprintBacklogDto(sprints);
    }

    public List<SprintPortfolioDto> getAllSprintPortfolioDto(Long projectId){
        return sprintRepository
                .findAllByProject_IdOrderByIdAsc(projectId)
                .stream()
                .map(sprintMapper::sprintToSprintPortfolioDto)
                .collect(Collectors.toList());
    }


}
