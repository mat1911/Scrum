package com.example.scrum.mappers;

import com.example.scrum.dto.AssignedUserDto;
import com.example.scrum.dto.UserRegisterDto;
import com.example.scrum.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRegisterDto userRegisterDto);
    AssignedUserDto toAssignedUserDto(User user);
}
