package com.example.scrum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryBacklogDto {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private Integer storyPoints;
    private String acceptanceCriteria;
    private Long sprintId;
}
