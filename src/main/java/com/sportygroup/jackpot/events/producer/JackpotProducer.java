package com.sportygroup.jackpot.events.producer;

import com.sportygroup.jackpot.events.model.BetEvent;
import com.sportygroup.jackpot.exception.SendMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JackpotProducer {

    private final KafkaTemplate<String, BetEvent> kafkaTemplate;

    public JackpotProducer(KafkaTemplate<String, BetEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    private final static String JACKPOT_TOPIC = "jackpot-bets";

    private static final Logger log = LoggerFactory.getLogger(JackpotProducer.class);

    public void send(BetEvent betEvent) {
        kafkaTemplate.send(JACKPOT_TOPIC, betEvent.userId(), betEvent)
                .thenAccept(result ->
                        log.info("Message sent, offset = {}", result.getRecordMetadata().offset())
                )
                .exceptionally(ex -> {
                    log.error("Error sending message", ex);
                    throw new SendMessageException("Error sending message: " + ex.getMessage());
                });
    }
}

