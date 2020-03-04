package com.example.scrum.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends AbstractEntity {

    private Long id;
    private String name;
    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "project")
    private Set<UserProject> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Story> stories = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Sprint> sprints = new HashSet<>();
}
