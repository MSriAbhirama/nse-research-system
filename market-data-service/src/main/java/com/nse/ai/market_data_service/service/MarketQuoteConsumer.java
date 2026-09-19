package com.nse.ai.market_data_service.service;

import com.nse.ai.market_data_service.dto.v1.MarketQuoteRequest;
import com.nse.ai.market_data_service.dto.v1.kafka.MarketQuoteEvent;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MarketQuoteConsumer {

    private final MarketDataService marketDataService;

    public MarketQuoteConsumer(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @KafkaListener(
            topics = "market-quotes",
            groupId = "market-data-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(MarketQuoteEvent event) {

        System.out.println(
                "Received market quote for: " + event.getSymbol()
        );

        System.out.println(
                "Price: " + event.getLastPrice()
        );

        marketDataService.saveOrUpdateQuote(
                convertToRequest(event)
        );
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