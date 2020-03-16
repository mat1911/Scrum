package com.example.scrum.service;

import com.example.scrum.dto.SprintPortfolioDto;
import com.example.scrum.entity.Sprint;
import com.example.scrum.exceptions.SprintNotFoundException;
import com.example.scrum.mappers.SprintMapper;
import com.example.scrum.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;

    public Sprint getCurrentSprint(Long projectId){
        return sprintRepository
                .findCurrentByProject_Id(projectId, LocalDate.now())
                .orElseThrow(() -> new SprintNotFoundException("Sprint with a such project Id does not exist!"));
    }

    public List<SprintPortfolioDto> getAllSprintPortfolioDto(Long projectId){
        return sprintRepository
                .findAllByProject_IdOrderByIdAsc(projectId)
                .stream()
                .map(sprintMapper::sprintToSprintPortfolioDto)
                .collect(Collectors.toList());
    }

}
