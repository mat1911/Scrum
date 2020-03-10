package com.example.scrum.mappers;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.entity.Project;
import com.example.scrum.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);

    @Mappings({
            @Mapping(source = "owner", target = "ownerId", qualifiedByName = "ownerToOwnerId")
    })
    ProjectDto toDto(Project project);


    @Named("ownerToOwnerId")
    static Long ownerToOwnerId(User owner){
        return owner.getId();
    }
}
