package com.nse.ai.market_data_service.dto.v1.kafka;

import lombok.Data;

import java.math.BigDecimal;

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