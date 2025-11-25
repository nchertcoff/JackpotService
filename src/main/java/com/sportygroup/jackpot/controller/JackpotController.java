package com.sportygroup.jackpot.controller;

import com.sportygroup.jackpot.dto.EvaluateBetRequestDto;
import com.sportygroup.jackpot.dto.EvaluateBetResponseDto;
import com.sportygroup.jackpot.dto.PublishBetRequestDto;
import com.sportygroup.jackpot.dto.PublishBetResponseDto;
import com.sportygroup.jackpot.events.model.BetEvent;
import com.sportygroup.jackpot.events.producer.JackpotProducer;
import com.sportygroup.jackpot.exception.MandatoryFieldNotFoundException;
import com.sportygroup.jackpot.mapper.JackpotMapper;
import com.sportygroup.jackpot.service.JackpotService;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/jackpots")
public class JackpotController {

    private final JackpotProducer producer;
    private final JackpotService service;

    public JackpotController(JackpotProducer producer, JackpotService service) {
        this.producer = producer;
        this.service = service;
    }

    @PostMapping("/bets")
    public PublishBetResponseDto publishBet(@RequestBody PublishBetRequestDto request) {
        validateRequest(request);

        BetEvent event = JackpotMapper.fromDtoToEvent(request);
        producer.send(event);
        return new PublishBetResponseDto(LocalDateTime.now());
    }

    private void validateRequest(PublishBetRequestDto request) {
        service.validateJackpot(request.jackpotId());
        if (StringUtils.isEmpty(request.betId()) || StringUtils.isEmpty(request.userId())
                || StringUtils.isEmpty(request.jackpotId()) || request.betAmount() == null) {
            throw new MandatoryFieldNotFoundException("Mandatory field not found");
        }
    }

    @PostMapping("/evaluate")
    public EvaluateBetResponseDto evaluateBet(@RequestBody EvaluateBetRequestDto request) {
        return service.evaluateAndConsume(request.betId());
    }


}
