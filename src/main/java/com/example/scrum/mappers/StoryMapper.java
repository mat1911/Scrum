package com.example.scrum.mappers;

import com.example.scrum.dto.StoryDto;
import com.example.scrum.entity.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    StoryDto toDto(Story story);
    Story toEntity(StoryDto storyDto);

}
