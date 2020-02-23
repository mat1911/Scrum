package com.example.scrum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "user_projects")
@NoArgsConstructor
@AllArgsConstructor
public class UserProject implements Serializable {

    @EmbeddedId
    private UserProjectId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @Column(name = "user_capacity")
    private Integer userCapacity;

}
