package com.example.scrum.mappers;

import com.example.scrum.dto.StoryBacklogDto;
import com.example.scrum.dto.StoryKanbanDto;
import com.example.scrum.entity.Sprint;
import com.example.scrum.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StoryMapper {

    @Mappings({
            @Mapping(source = "sprint", target = "sprintId", qualifiedByName = "sprintToSprintId")
    })
    StoryBacklogDto entityToStoryBacklogDto(Story story);

    @Named("storiesToStoriesBacklogDto")
    List<StoryBacklogDto> entityToStoryBacklogDto(List<Story> story);
    Story storyBacklogToEntity(StoryBacklogDto storyDto);

    @Mapping(source = "assignedUser", target = "assignedUserDto", qualifiedByName = "toAssignedUserDto")
    StoryKanbanDto entityToStoryKanban(Story story);

    @Named("sprintToSprintId")
    static Long sprintToSprintId(Sprint sprint){
        return (sprint == null || sprint.getId() == null) ? null : sprint.getId();
    }
}
