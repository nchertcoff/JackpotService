package com.sportygroup.jackpot.strategy.contribution;

public interface ContributionStrategy {
    double calculateContribution(double betAmount, double poolSize);
}
