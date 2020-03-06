package com.example.scrum.service;

import com.example.scrum.entity.Sprint;
import com.example.scrum.exceptions.ObjectNotFoundException;
import com.example.scrum.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    public Sprint getCurrentSprint(Long projectId){
        return sprintRepository
                .findCurrentByProject_Id(projectId, LocalDate.now())
                .orElseThrow(() -> new ObjectNotFoundException("Sprint with a such project Id does not exist!"));
    }

}
