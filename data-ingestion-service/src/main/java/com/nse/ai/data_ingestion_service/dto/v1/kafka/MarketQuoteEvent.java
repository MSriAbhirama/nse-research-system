package com.nse.ai.data_ingestion_service.dto.v1.kafka;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MarketQuoteEvent {
    private String symbol;
    private String instrumentKey;
    private BigDecimal lastPrice;
    private BigDecimal previousClose;
    private BigDecimal openPrice;
    private BigDecimal dayHigh;
    private BigDecimal dayLow;
    private Long volume;
    private BigDecimal fiftyTwoWeekHigh;
    private BigDecimal fiftyTwoWeekLow;
}
