package com.example.scrum.repository;


import com.example.scrum.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findAllByProject_IdOrderByIdAsc(Long projectId);

    @Query("select s from Sprint s where s.id=:projectId and s.beginDate <= :todayDate and s.endDate >= :todayDate")
    Optional<Sprint> findCurrentByProject_Id(@Param("projectId")Long projectId, @Param("todayDate") LocalDate todayDate);
}
