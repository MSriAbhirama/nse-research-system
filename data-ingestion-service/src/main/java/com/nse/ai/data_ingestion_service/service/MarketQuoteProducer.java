package com.nse.ai.data_ingestion_service.service;

import com.nse.ai.data_ingestion_service.dto.v1.kafka.MarketQuoteEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MarketQuoteProducer {

    private static final String TOPIC = "market-quotes";

    private final KafkaTemplate<String, MarketQuoteEvent> kafkaTemplate;

    public MarketQuoteProducer(
            KafkaTemplate<String, MarketQuoteEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(MarketQuoteEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getSymbol(),
                event
        );
    }
}