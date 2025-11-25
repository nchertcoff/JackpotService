package com.sportygroup.jackpot.db.repository;

import com.sportygroup.jackpot.db.entity.Contribution;
import com.sportygroup.jackpot.db.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, String> {
    Optional<Reward> findByBetId(String betId);
}