package com.example.scrum.mappers;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);
    ProjectDto toDto(Project project);
}
