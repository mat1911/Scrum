package com.example.scrum.dto;

import com.example.scrum.entity.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StoryDto {

    private Long id;
    private String title;
    private Long number;
    private String shortDescription;
    private String description;
    private Integer storyPoints;
    private String acceptanceCriteria;
    private Status status;
    private AssignedUserDto assignedUserDto;

}
