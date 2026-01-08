package com.app.repository;

import com.app.entity.PreviousWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreviousWorkRepository extends JpaRepository<PreviousWork, Long> {
}
