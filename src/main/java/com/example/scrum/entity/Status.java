package com.example.scrum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "statuses")
@NoArgsConstructor
@AllArgsConstructor
public class Status extends AbstractEntity{

    @Column(nullable = false, unique = true)
    private String name;

}
