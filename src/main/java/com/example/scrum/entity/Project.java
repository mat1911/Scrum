package com.example.scrum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Project extends AbstractEntity {

    private String name;

    private String description;

    @OneToMany(mappedBy = "project")
    private Set<UserProject> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Story> stories = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Sprint> sprints = new HashSet<>();
}
