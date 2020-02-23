package com.example.scrum.repository;

import com.example.scrum.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findByOrderByIdAsc();
}
