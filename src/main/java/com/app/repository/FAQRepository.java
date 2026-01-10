package com.app.repository;

import com.app.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    Optional<FAQ> findBySlug(String slug);
}
