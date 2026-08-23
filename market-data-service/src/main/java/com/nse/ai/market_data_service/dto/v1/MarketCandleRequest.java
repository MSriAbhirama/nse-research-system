package com.nse.ai.market_data_service.dto.v1;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class MarketCandleRequest {

    private String symbol;
    private String interval;

    private OffsetDateTime candleTimestamp;

    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closePrice;

    private Long volume;
}