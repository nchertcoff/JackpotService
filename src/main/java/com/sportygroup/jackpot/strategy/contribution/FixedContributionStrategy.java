package com.sportygroup.jackpot.strategy.contribution;

public class FixedContributionStrategy implements ContributionStrategy {

    private final double fixedPercentage;

    public FixedContributionStrategy(double fixedPercentage) {
        this.fixedPercentage = fixedPercentage;
    }

    @Override
    public double calculateContribution(double betAmount, double poolSize) {
        return betAmount * fixedPercentage;
    }
}

