package com.sportygroup.jackpot.strategy.contribution;

public class VariableContributionStrategy implements ContributionStrategy {

    private final double maxPercentage;
    private final double minPercentage;
    private final double maxPoolSize;

    public VariableContributionStrategy(double maxPercentage, double minPercentage, double maxPoolSize) {
        this.maxPercentage = maxPercentage;
        this.minPercentage = minPercentage;
        this.maxPoolSize = maxPoolSize;
    }

    @Override
    public double calculateContribution(double betAmount, double poolSize) {
        double ratio = poolSize / maxPoolSize;
        if (ratio > 1) {
            ratio = 1;
        }

        double percentage = maxPercentage - (maxPercentage - minPercentage) * ratio;
        return betAmount * percentage;
    }
}

