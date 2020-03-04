package com.example.scrum.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "user_projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProject implements Serializable {

    @EmbeddedId
    private UserProjectId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("projectId")
    private Project project;

    @Column(name = "user_capacity")
    private Integer userCapacity;

}
