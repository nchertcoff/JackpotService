package com.sportygroup.jackpot.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(unique=true)
    private String betId;

    @NotNull
    private String userId;

    @NotNull
    private String jackpotId;

    @NotNull
    private BigDecimal jackpotRewardAmount;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    public Reward() {
    }

    public Reward(String betId, String userId, String jackpotId, BigDecimal jackpotRewardAmount) {
        this.betId = betId;
        this.userId = userId;
        this.jackpotId = jackpotId;
        this.jackpotRewardAmount = jackpotRewardAmount;
    }

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(String jackpotId) {
        this.jackpotId = jackpotId;
    }

    public BigDecimal getJackpotRewardAmount() {
        return jackpotRewardAmount;
    }

    public void setJackpotRewardAmount(BigDecimal jackpotRewardAmount) {
        this.jackpotRewardAmount = jackpotRewardAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
