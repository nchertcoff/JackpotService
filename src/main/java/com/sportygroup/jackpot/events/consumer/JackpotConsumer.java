package com.sportygroup.jackpot.events.consumer;

import com.sportygroup.jackpot.events.model.BetEvent;
import com.sportygroup.jackpot.service.JackpotService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class JackpotConsumer {

    private final JackpotService jackpotService;

    public JackpotConsumer(JackpotService jackpotService) {
        this.jackpotService = jackpotService;
    }

    @KafkaListener(
            topics = "jackpot-bets",
            groupId = "jackpot-group"
    )
    public void consume(BetEvent event, Acknowledgment ack) {
        try {
            // update jackpot
            jackpotService.applyEvent(event);

            // confirm offset if everything went well
            ack.acknowledge();

        } catch (Exception ex) {
            // we don't ack → Kafka will deliver this msg again
            throw ex;
        }
    }
}


