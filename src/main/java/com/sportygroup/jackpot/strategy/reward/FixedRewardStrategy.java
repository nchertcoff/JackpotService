package com.sportygroup.jackpot.strategy.reward;

public class FixedRewardStrategy implements RewardStrategy {

    private final double fixedPercentage;

    public FixedRewardStrategy(double fixedPercentage) {
        this.fixedPercentage = fixedPercentage;
    }

    @Override
    public double calculateChance(double jackpotPool) {
        return fixedPercentage;
    }
}

