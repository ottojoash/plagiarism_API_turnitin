package com.example.palagrism.repository;


import com.example.palagrism.model.TurnitinSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnitinSubmissionRepository extends JpaRepository<TurnitinSubmission, Long> {
}
