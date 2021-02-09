package com.example.scrum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sprints")
@NoArgsConstructor
@AllArgsConstructor
public class Sprint extends AbstractEntity{

    private String title;

    private String description;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "sprint", fetch = FetchType.EAGER)
    private List<Story> stories = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;


}
