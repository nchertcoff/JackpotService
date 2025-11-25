package com.sportygroup.jackpot.strategy.service;

import com.sportygroup.jackpot.db.entity.Contribution;
import com.sportygroup.jackpot.db.entity.Jackpot;
import com.sportygroup.jackpot.db.repository.ContributionRepository;
import com.sportygroup.jackpot.db.repository.JackpotRepository;
import com.sportygroup.jackpot.dto.EvaluateBetResponseDto;
import com.sportygroup.jackpot.events.model.BetEvent;
import com.sportygroup.jackpot.service.JackpotService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JackpotServiceIntegrationTest {

    @Autowired
    JackpotService service;

    @Autowired
    ContributionRepository contributionRepo;
    @Autowired
    JackpotRepository jackpotRepo;

    @Test
    @Order(1)
    void testContribution() {
        BetEvent betEvent = new BetEvent("1", "1", "FIXED", new BigDecimal("1000"));
        service.applyEvent(betEvent);

        Contribution contribution = contributionRepo.findByBetId("1").orElseThrow();

        assertEquals("FIXED", contribution.getJackpotId());
        assertEquals(new BigDecimal("10.00"), contribution.getContributionAmount());
        assertEquals(new BigDecimal("1010.00"), contribution.getCurrentJackpotAmount());
        assertEquals(false, contribution.getConsumed());

        Jackpot jackpot = jackpotRepo.findByJackpotId("FIXED").get();
        assertEquals(new BigDecimal("1010.00"), jackpot.getAmount());
        assertEquals("FIXED", jackpot.getJackpotId());

    }

    @Test
    @Order(2)
    void testSecondContribution() {
        BetEvent betEvent = new BetEvent("2", "1", "FIXED", new BigDecimal("1000"));
        service.applyEvent(betEvent);

        Contribution contribution = contributionRepo.findByBetId("2").orElseThrow();

        assertEquals("FIXED", contribution.getJackpotId());
        assertEquals(new BigDecimal("10.00"), contribution.getContributionAmount());
        assertEquals(new BigDecimal("1020.00"), contribution.getCurrentJackpotAmount());
        assertEquals(false, contribution.getConsumed());

        Jackpot jackpot = jackpotRepo.findByJackpotId("FIXED").get();
        assertEquals(new BigDecimal("1020.00"), jackpot.getAmount());
        assertEquals("FIXED", jackpot.getJackpotId());

    }

    @Test
    @Order(3)
    void testEvaluate() {
        service.evaluateAndConsume("1");
        Contribution contribution = contributionRepo.findByBetId("1").orElseThrow();
        assertEquals(true, contribution.getConsumed());
    }
}
