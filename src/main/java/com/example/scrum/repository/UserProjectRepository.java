package com.example.scrum.repository;

import com.example.scrum.entity.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findAllByUserId(Long id);
}
