package com.app.repository;

import com.app.entity.PreviousWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreviousWorkRepository extends JpaRepository<PreviousWork, Long> {
    Optional<PreviousWork> findBySlug(String slug);
}
