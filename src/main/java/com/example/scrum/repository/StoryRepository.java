package com.example.scrum.repository;

import com.example.scrum.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByProject_IdAndStatus_Name(Long projectId, String status);

    List<Story> findAllByProject_IdAndSprint_IdAndStatus_Name(Long projectId, Long sprintId, String status);

    List<Story> findAllByProject_Id(Long projectId);
}
