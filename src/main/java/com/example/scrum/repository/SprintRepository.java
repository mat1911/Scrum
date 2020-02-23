package com.example.scrum.repository;


import com.example.scrum.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findAllByProject_IdOrderByIdAsc(Long projectId);
}
