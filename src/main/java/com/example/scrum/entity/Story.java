package com.example.scrum.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "stories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story extends AbstractEntity {

    private String title;

    private Long number;

    @Column(name = "short_description")
    private String shortDescription;

    private String description;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "acceptance_criteria")
    private String acceptanceCriteria;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedTo;
}
