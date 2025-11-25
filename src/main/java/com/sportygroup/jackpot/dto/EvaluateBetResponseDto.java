package com.sportygroup.jackpot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EvaluateBetResponseDto(
        Boolean won,
        BigDecimal price
) {}
