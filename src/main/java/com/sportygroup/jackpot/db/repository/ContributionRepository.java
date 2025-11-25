package com.sportygroup.jackpot.db.repository;

import com.sportygroup.jackpot.db.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, String> {
    Optional<Contribution> findByBetId(String betId);
}