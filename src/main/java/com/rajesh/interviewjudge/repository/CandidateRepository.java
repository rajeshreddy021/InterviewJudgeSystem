package com.rajesh.interviewjudge.repository;

import com.rajesh.interviewjudge.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository
        extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);
}