package com.sportygroup.jackpot.strategy.contribution;

import com.sportygroup.jackpot.properties.JackpotConfig;
import com.sportygroup.jackpot.properties.JackpotProperties;
import org.springframework.stereotype.Component;

@Component
public class ContributionStrategyFactory {

    private final JackpotConfig jackpotConfig;

    public ContributionStrategyFactory(JackpotConfig jackpotConfig) {
        this.jackpotConfig = jackpotConfig;
    }

    public ContributionStrategy getStrategy(String jackpotId) {
        JackpotProperties props = jackpotConfig.getContribution().get(jackpotId);
        if (props == null) {
            throw new IllegalArgumentException("No configuration found for jackpotId: " + jackpotId);
        }

        if ("FIXED".equalsIgnoreCase(props.getType())) {
            return new FixedContributionStrategy(props.getFixedPercentage().doubleValue());
        } else if ("VARIABLE".equalsIgnoreCase(props.getType())) {
            return new VariableContributionStrategy(
                    props.getMaxPercentage().doubleValue(),
                    props.getMinPercentage().doubleValue(),
                    props.getMaxPool().doubleValue()
            );
        } else {
            throw new IllegalArgumentException("Unknown contribution type: " + props.getType());
        }
    }
}
