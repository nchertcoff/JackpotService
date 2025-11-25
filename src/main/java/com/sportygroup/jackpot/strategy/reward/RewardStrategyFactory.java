package com.sportygroup.jackpot.strategy.reward;

import com.sportygroup.jackpot.properties.JackpotConfig;
import com.sportygroup.jackpot.properties.JackpotProperties;
import com.sportygroup.jackpot.strategy.contribution.ContributionStrategy;
import com.sportygroup.jackpot.strategy.contribution.FixedContributionStrategy;
import com.sportygroup.jackpot.strategy.contribution.VariableContributionStrategy;
import org.springframework.stereotype.Component;

@Component
public class RewardStrategyFactory {

    private final JackpotConfig jackpotConfig;

    public RewardStrategyFactory(JackpotConfig jackpotConfig) {
        this.jackpotConfig = jackpotConfig;
    }

    public RewardStrategy getStrategy(String jackpotId) {
        JackpotProperties props = jackpotConfig.getReward().get(jackpotId);
        if (props == null) {
            throw new IllegalArgumentException("No configuration found for jackpotId: " + jackpotId);
        }

        if ("FIXED".equalsIgnoreCase(props.getType())) {
            return new FixedRewardStrategy(props.getFixedPercentage().doubleValue());
        } else if ("VARIABLE".equalsIgnoreCase(props.getType())) {
            return new VariableRewardStrategy(
                    props.getMaxPercentage().doubleValue(),
                    props.getMinPercentage().doubleValue(),
                    props.getMaxPool().doubleValue(),
                    props.getInitialAmount().doubleValue()
            );
        } else {
            throw new IllegalArgumentException("Unknown contribution type: " + props.getType());
        }
    }
}
