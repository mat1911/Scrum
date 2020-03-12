package com.example.scrum.mappers;

import com.example.scrum.dto.AssignedUserDto;
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

    @Autowired
    private UserMapper userMapper;

    @Mapping(source = "assignedUserDto", target = "assignedUser", qualifiedByName = "toAssignedUser")
    public abstract Story toEntity(StoryDto storyDto);

    @Mappings({
            @Mapping(source = "assignedUser", target = "assignedUserDto", qualifiedByName = "toAssignedUserDto")
    })
    public abstract StoryDto toDto(Story story);

    @Named("toAssignedUserDto")
     public AssignedUserDto toAssignedUserDto(User assignedUser){

        AssignedUserDto user = userMapper.toAssignedUserDto(assignedUser);

        return user == null ? new AssignedUserDto() : user;
    }

    @Named("toAssignedUser")
    public  User toAssignedUser(AssignedUserDto assignedUserDto){
        return (assignedUserDto != null && assignedUserDto.getId() != null) ? userService.findById(assignedUserDto.getId()) : null;
    }

}
