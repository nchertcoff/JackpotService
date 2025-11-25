package com.sportygroup.jackpot.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jackpots")
public class JackpotConfig {
    private Map<String, JackpotProperties> contribution;
    public Map<String, JackpotProperties> getContribution() { return contribution; }
    public void setContribution(Map<String, JackpotProperties> contribution) { this.contribution = contribution; }

    private Map<String, JackpotProperties> reward;
    public Map<String, JackpotProperties> getReward() { return reward; }
    public void setReward(Map<String, JackpotProperties> reward) { this.reward = reward; }

}

