package com.sportygroup.jackpot.strategy.contribution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContributionStrategyTest {

    @Test
    void fixedStrategy() {
        FixedContributionStrategy strategy = new FixedContributionStrategy(0.05);
        double contribution = strategy.calculateContribution(1000, 10000);
        assertEquals(50, contribution);
    }

    @Test
    void variableStrategy() {
        VariableContributionStrategy strategy = new VariableContributionStrategy(0.04, 0.001, 100000);
        double contribution = strategy.calculateContribution(1000, 10000);
        assertEquals(36.1, contribution);

        contribution = strategy.calculateContribution(1000, 20000);
        assertEquals(32.2, contribution);
    }
}

