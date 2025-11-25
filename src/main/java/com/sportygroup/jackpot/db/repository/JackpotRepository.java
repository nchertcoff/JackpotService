package com.sportygroup.jackpot.db.repository;

import com.sportygroup.jackpot.db.entity.Contribution;
import com.sportygroup.jackpot.db.entity.Jackpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JackpotRepository extends JpaRepository<Jackpot, String> {
    Optional<Jackpot> findByJackpotId(String jackpotId);
}