package com.nse.ai.data_ingestion_service.service;

import com.nse.ai.data_ingestion_service.dto.v1.kafka.MarketQuoteEvent;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class MarketQuoteProducer {

    private static final String TOPIC = "market-quotes";

    private static final Logger log =
            LoggerFactory.getLogger(MarketQuoteProducer.class);

    private final KafkaTemplate<String, MarketQuoteEvent> kafkaTemplate;

    public MarketQuoteProducer(
            KafkaTemplate<String, MarketQuoteEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public CompletableFuture<SendResult<String, MarketQuoteEvent>> publish(
            MarketQuoteEvent event) {

        try {
            return kafkaTemplate.send(
                    TOPIC,
                    event.getSymbol(),
                    event
            ).whenComplete((result, exception) -> {

                if (exception != null) {
                    log.error(
                            "Failed to publish market quote for symbol: {}",
                            event.getSymbol(),
                            exception
                    );
                } else {
                    RecordMetadata metadata = result.getRecordMetadata();

                    log.info(
                            "Successfully published quote. Symbol: {}, Topic: {}, Partition: {}, Offset: {}",
                            event.getSymbol(),
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset()
                    );
                }
            });

        } catch (Exception exception) {

            log.error(
                    "Kafka producer failed before sending quote for symbol: {}",
                    event.getSymbol(),
                    exception
            );

            return CompletableFuture.failedFuture(exception);
        }
    }

}