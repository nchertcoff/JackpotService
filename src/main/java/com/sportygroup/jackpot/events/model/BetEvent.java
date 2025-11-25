package com.sportygroup.jackpot.events.model;

import java.math.BigDecimal;

public record BetEvent (
    String betId,
    String userId,
    String jackpotId,
    BigDecimal betAmount
) {}
