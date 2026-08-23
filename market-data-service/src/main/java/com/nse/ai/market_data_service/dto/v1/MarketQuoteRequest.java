package com.nse.ai.market_data_service.dto.v1;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class MarketQuoteRequest {

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

    private OffsetDateTime lastUpdatedAt;
}