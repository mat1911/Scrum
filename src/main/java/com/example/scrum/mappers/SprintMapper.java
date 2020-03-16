package com.example.scrum.mappers;

import com.example.scrum.dto.SprintPortfolioDto;
import com.example.scrum.entity.Sprint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SprintMapper {

    SprintPortfolioDto sprintToSprintPortfolioDto(Sprint sprint);

}
