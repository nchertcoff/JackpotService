package com.sportygroup.jackpot.service;

import com.sportygroup.jackpot.db.entity.Contribution;
import com.sportygroup.jackpot.db.entity.Jackpot;
import com.sportygroup.jackpot.db.entity.Reward;
import com.sportygroup.jackpot.db.repository.ContributionRepository;
import com.sportygroup.jackpot.db.repository.JackpotRepository;
import com.sportygroup.jackpot.db.repository.RewardRepository;
import com.sportygroup.jackpot.dto.EvaluateBetResponseDto;
import com.sportygroup.jackpot.events.model.BetEvent;
import com.sportygroup.jackpot.exception.JackpotConfigurationNotFoundException;
import com.sportygroup.jackpot.mapper.JackpotMapper;
import com.sportygroup.jackpot.properties.JackpotConfig;
import com.sportygroup.jackpot.strategy.contribution.ContributionStrategy;
import com.sportygroup.jackpot.strategy.contribution.ContributionStrategyFactory;
import com.sportygroup.jackpot.strategy.reward.RewardStrategy;
import com.sportygroup.jackpot.strategy.reward.RewardStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class JackpotService {

    private final ContributionRepository contributionRepo;
    private final RewardRepository rewardRepo;
    private final JackpotRepository jackpotRepo;
    private final ContributionStrategyFactory contributionStrategyFactory;
    private final RewardStrategyFactory rewardStrategyFactory;
    private static final Logger log = LoggerFactory.getLogger(JackpotService.class);
    private final JackpotConfig jackpotConfig;

    public JackpotService(ContributionRepository contributionRepo, RewardRepository rewardRepo, JackpotRepository jackpotRepo,
                          ContributionStrategyFactory contributionStrategyFactory,
                          RewardStrategyFactory rewardStrategyFactory,
                          JackpotConfig jackpotConfig) {
        this.contributionRepo = contributionRepo;
        this.rewardRepo = rewardRepo;
        this.jackpotRepo = jackpotRepo;
        this.contributionStrategyFactory = contributionStrategyFactory;
        this.rewardStrategyFactory = rewardStrategyFactory;
        this.jackpotConfig = jackpotConfig;
    }

    @Transactional
    public synchronized void applyEvent(BetEvent betEvent) {

        Jackpot jackpot = getOrCreateJackpot(betEvent.jackpotId());
        BigDecimal jackpotPool = jackpot.getAmount();

        try {
            String jackpotId = betEvent.jackpotId();
            BigDecimal contributionAmount = getContributionAmount(betEvent, jackpotId, jackpotPool);
            jackpotPool = jackpotPool.add(contributionAmount);
            log.info("contribution = {}", contributionAmount);
            log.info("jackpotPool = {}", jackpotPool);

            saveContributionAndUpdateJackpot(betEvent, jackpot, contributionAmount, jackpotPool);
        } catch (Exception e) {
            //TODO: Define error handling/reprocess strategy
            log.error("Error saving contribution", e);
        }

    }

    private BigDecimal getContributionAmount(BetEvent betEvent, String jackpotId, BigDecimal jackpotPool) {
        ContributionStrategy contributionStrategy = contributionStrategyFactory.getStrategy(jackpotId);
        return new BigDecimal(contributionStrategy.calculateContribution(
                betEvent.betAmount().doubleValue(), jackpotPool.doubleValue()));
    }

    private void saveContributionAndUpdateJackpot(BetEvent betEvent, Jackpot jackpot, BigDecimal contributionAmount, BigDecimal jackpotPool) {
        Contribution contribution = JackpotMapper.fromEventToDBContribution(betEvent, contributionAmount, jackpotPool);
        contributionRepo.save(contribution);

        jackpot.setAmount(jackpotPool);
        jackpotRepo.save(jackpot);
    }

    private Jackpot getOrCreateJackpot(String jackpotId) {
        Optional<Jackpot> optionalJackpot = jackpotRepo.findByJackpotId(jackpotId);
        if (optionalJackpot.isEmpty()) {
            Jackpot jackpot = new Jackpot();
            jackpot.setJackpotId(jackpotId);
            log.info("initialAmount = {}", jackpotConfig.getReward().get(jackpotId).getInitialAmount());
            jackpot.setAmount(jackpotConfig.getReward().get(jackpotId).getInitialAmount());
            return jackpotRepo.save(jackpot);
        }
        return optionalJackpot.get();
    }

    public void validateJackpot(String jackpotId) {
        if (jackpotConfig.getReward().get(jackpotId) == null) {
            throw new JackpotConfigurationNotFoundException("Jackpot configuration not found: " + jackpotId);
        }
    }

    @Transactional
    public EvaluateBetResponseDto evaluateAndConsume(String betId) {

        Contribution contribution = contributionRepo.findByBetId(betId)
                .orElseThrow(() -> new IllegalArgumentException("Bet not found: " + betId));
        if (contribution.getConsumed()) {
            return respondPreviousContribution(betId);
        }
        contribution.setConsumed(true);
        contributionRepo.save(contribution);

        String jackpotId = contribution.getJackpotId();
        boolean isWinner = evaluateWinner(jackpotId, contribution.getCurrentJackpotAmount());
        if (isWinner) {
            log.info("There's a winner!");
            return createRewardAndResetJackpot(betId, jackpotId, contribution);
        }

        return new EvaluateBetResponseDto(false, null);
    }

    private EvaluateBetResponseDto createRewardAndResetJackpot(String betId, String jackpotId, Contribution contribution) {
        Jackpot jackpot = jackpotRepo.findByJackpotId(jackpotId).get();
        jackpot.setAmount(jackpotConfig.getReward().get(jackpotId).getInitialAmount());
        jackpotRepo.save(jackpot);

        Reward reward = new Reward(betId, contribution.getUserId(), contribution.getJackpotId(), contribution.getCurrentJackpotAmount());
        rewardRepo.save(reward);
        return new EvaluateBetResponseDto(true, contribution.getCurrentJackpotAmount());
    }

    private boolean evaluateWinner(String jackpotId, BigDecimal currentJackpot) {
        RewardStrategy rewardStrategy = rewardStrategyFactory.getStrategy(jackpotId);
        double chance = rewardStrategy.calculateChance(currentJackpot.doubleValue());
        log.info("chance = {}", chance);
        return Math.random() < chance;
    }

    private EvaluateBetResponseDto respondPreviousContribution(String betId) {
        Optional<Reward> reward = rewardRepo.findByBetId(betId);
        if (reward.isEmpty()) {
            return new EvaluateBetResponseDto(false, null);
        } else {
            return new EvaluateBetResponseDto(true, reward.get().getJackpotRewardAmount());
        }
    }

}