package com.goodamcodes.repository;

import com.goodamcodes.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {
    Optional<Education> findByProgramme(String programme);
}
