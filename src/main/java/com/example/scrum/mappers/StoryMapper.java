package com.example.scrum.mappers;

import com.example.scrum.dto.StoryDto;
import com.example.scrum.entity.Story;
import com.example.scrum.entity.User;
import com.example.scrum.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class StoryMapper {

    @Autowired
    private UserService userService;

    @Mapping(source = "assignedUserId", target = "assignedUser", qualifiedByName = "toAssignedUser")
    public abstract Story toEntity(StoryDto storyDto);

    @Mappings({
            @Mapping(source = "assignedUser", target = "assignedUserId", qualifiedByName = "toAssignedUserId")
    })
    public abstract StoryDto toDto(Story story);

    @Named("toAssignedUserId")
     public Long toAssignedUserId(User assignedUser){
        return assignedUser != null ? assignedUser.getId() : null;
    }

    @Named("toAssignedUser")
    public  User toAssignedUser(Long assignedUserId){
        return assignedUserId != null ? userService.findById(assignedUserId) : null;
    }

}
