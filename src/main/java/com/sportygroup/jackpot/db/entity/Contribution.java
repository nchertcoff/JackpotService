package com.sportygroup.jackpot.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Contribution {
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
    private BigDecimal stakeAmount;

    @NotNull
    private BigDecimal contributionAmount;


    @NotNull
    private BigDecimal currentJackpotAmount;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private Boolean consumed = false;

    public Contribution() {
    }

    public Contribution(String betId, String userId, String jackpotId, BigDecimal stakeAmount,
                        BigDecimal contributionAmount, BigDecimal currentJackpotAmount) {
        this.betId = betId;
        this.userId = userId;
        this.jackpotId = jackpotId;
        this.stakeAmount = stakeAmount;
        this.contributionAmount = contributionAmount;
        this.currentJackpotAmount = currentJackpotAmount;
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

    public BigDecimal getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(BigDecimal stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public BigDecimal getCurrentJackpotAmount() {
        return currentJackpotAmount;
    }

    public void setCurrentJackpotAmount(BigDecimal currentJackpotAmount) {
        this.currentJackpotAmount = currentJackpotAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getConsumed() {
        return consumed;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }
}
