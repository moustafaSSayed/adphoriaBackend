package com.app.repository;

import com.app.entity.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
    Optional<Research> findBySlug(String slug);
}
