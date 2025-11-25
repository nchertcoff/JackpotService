package com.sportygroup.jackpot.strategy.reward;

public class VariableRewardStrategy implements RewardStrategy {

    private final double maxPercentage;
    private final double minPercentage;
    private final double maxPoolSize;
    private final double initialAmount;

    public VariableRewardStrategy(double maxPercentage, double minPercentage, double maxPoolSize, double initialAmount) {
        this.maxPercentage = maxPercentage;
        this.minPercentage = minPercentage;
        this.maxPoolSize = maxPoolSize;
        this.initialAmount = initialAmount;
    }

    @Override
    public double calculateChance(double poolSize) {
        if (poolSize >= maxPoolSize) {
            return 1.0; // 100%
        }

        double ratio = (poolSize - initialAmount) / maxPoolSize;

        double baseChance = minPercentage +
                ratio * (maxPercentage - minPercentage);

        return Math.min(baseChance, 1.0);
    }
}

