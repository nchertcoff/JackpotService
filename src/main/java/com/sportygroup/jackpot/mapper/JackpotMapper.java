package com.sportygroup.jackpot.mapper;

import com.sportygroup.jackpot.dto.PublishBetRequestDto;
import com.sportygroup.jackpot.dto.PublishBetResponseDto;
import com.sportygroup.jackpot.db.entity.Contribution;
import com.sportygroup.jackpot.events.model.BetEvent;

import java.math.BigDecimal;

public class JackpotMapper {

    public static Contribution fromEventToDBContribution(BetEvent request, BigDecimal contributionAmount,
                                                         BigDecimal currentJackpotAmount) {
        return new Contribution(request.betId(), request.userId(), request.jackpotId(),
                request.betAmount(), contributionAmount,
                currentJackpotAmount);
    }

    public static BetEvent fromDtoToEvent(PublishBetRequestDto request) {
        return new BetEvent(
                request.betId(),
                request.userId(),
                request.jackpotId(),
                request.betAmount()
        );
    }
}

