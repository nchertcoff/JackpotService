package com.sportygroup.jackpot.dto;

import java.math.BigDecimal;

public record PublishBetRequestDto(
        String betId,
        String userId,
        String jackpotId,
        BigDecimal betAmount
) {}
