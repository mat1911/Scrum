package com.example.scrum.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SprintPortfolioDto {

    private String title;
    private String description;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Set<StoryDto> stories = new HashSet<>();
}
