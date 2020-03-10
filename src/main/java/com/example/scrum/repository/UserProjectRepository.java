package com.example.scrum.repository;

import com.example.scrum.entity.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findAllByUserId(Long userID);
    List<UserProject> findAllByProjectId(Long projectId);
    Long deleteByUserIdAndProjectId(Long userId, Long projectId);

}
