package com.nse.ai.market_data_service.service;

import com.nse.ai.market_data_service.dto.v1.MarketQuoteRequest;
import com.nse.ai.market_data_service.dto.v1.kafka.MarketQuoteEvent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MarketQuoteConsumer {

    private final MarketDataService marketDataService;

    private static final Logger log =
        LoggerFactory.getLogger(MarketQuoteConsumer.class);

    public MarketQuoteConsumer(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @KafkaListener(
        topics = "market-quotes",
        groupId = "market-data-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, MarketQuoteEvent> record) {

        MarketQuoteEvent event = record.value();

        log.info(
            "Received market quote. Symbol: {}, Partition: {}, Offset: {}",
            event.getSymbol(),
            record.partition(),
            record.offset()
        );

        try {

            marketDataService.saveOrUpdateQuote(convertToRequest(event));

            log.info(
                "Successfully processed market quote. Symbol: {}, Price: {}, Partition: {}, Offset: {}",
                event.getSymbol(),
                event.getLastPrice(),
                record.partition(),
                record.offset()
            );

        } catch (RuntimeException exception) {

            log.error(
                "Failed to process market quote. Symbol: {}, Partition: {}, Offset: {}",
                event.getSymbol(),
                record.partition(),
                record.offset(),
                exception
            );

            throw exception;
        }
    }
    private MarketQuoteRequest convertToRequest(MarketQuoteEvent event) {

        MarketQuoteRequest request = new MarketQuoteRequest();

        request.setSymbol(event.getSymbol());
        request.setInstrumentKey(event.getInstrumentKey());
        request.setLastPrice(event.getLastPrice());
        request.setPreviousClose(event.getPreviousClose());
        request.setOpenPrice(event.getOpenPrice());
        request.setDayHigh(event.getDayHigh());
        request.setDayLow(event.getDayLow());
        request.setVolume(event.getVolume());
        request.setFiftyTwoWeekHigh(event.getFiftyTwoWeekHigh());
        request.setFiftyTwoWeekLow(event.getFiftyTwoWeekLow());

        return request;
    }
}