package com.sportygroup.jackpot.strategy.reward;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RewardStrategyTest {

    @Test
    void fixedStrategy() {
        FixedRewardStrategy strategy = new FixedRewardStrategy(0.05);
        double change = strategy.calculateChance(10000);
        assertEquals(0.05, change);
    }

    @Test
    void variableStrategy() {
        VariableRewardStrategy strategy = new VariableRewardStrategy(1, 0.001, 100000, 1000);

        double chance = strategy.calculateChance(1000);
        assertEquals(0.001, chance);

        chance = strategy.calculateChance(2000);
        assertEquals(0.01099, chance);

        chance = strategy.calculateChance(5000);
        assertEquals(0.04096, chance);

        chance = strategy.calculateChance(10000);
        assertTrue(Math.abs(chance - 0.09090999) < 1e-6);

        chance = strategy.calculateChance(30000);
        assertTrue(Math.abs(chance - 0.29070999) < 1e-6);

        chance = strategy.calculateChance(100000);
        assertEquals(1.0, chance);
    }
}

