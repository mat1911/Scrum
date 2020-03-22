package com.example.scrum.mappers;

import com.example.scrum.dto.SprintBacklogDto;
import com.example.scrum.dto.SprintPortfolioDto;
import com.example.scrum.entity.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StoryMapper.class})
public interface SprintMapper {

    SprintPortfolioDto sprintToSprintPortfolioDto(Sprint sprint);

    @Mapping(source = "stories", target = "storiesDto", qualifiedByName = "storiesToStoriesBacklogDto")
    SprintBacklogDto sprintToSprintBacklogDto(Sprint sprint);
    List<SprintBacklogDto> sprintToSprintBacklogDto(List<Sprint> sprint);
    Sprint sprintBacklogToSprint(SprintBacklogDto sprintDto);

}
