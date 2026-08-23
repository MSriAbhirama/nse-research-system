package com.nse.ai.market_data_service.model;


import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "market_quote")
@Data
public class MarketQuoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column(name = "instrument_key", nullable = false)
    private String instrumentKey;

    @Column(name = "last_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal lastPrice;

    @Column(name = "previous_close", nullable = false, precision = 20, scale = 4)
    private BigDecimal previousClose;

    @Column(name = "open_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal openPrice;

    @Column(name = "day_high", nullable = false, precision = 20, scale = 4)
    private BigDecimal dayHigh;

    @Column(name = "day_low", nullable = false, precision = 20, scale = 4)
    private BigDecimal dayLow;

    @Column(nullable = false)
    private Long volume;

    @Column(name = "fifty_two_week_high", nullable = false, precision = 20, scale = 4)
    private BigDecimal fiftyTwoWeekHigh;

    @Column(name = "fifty_two_week_low", nullable = false, precision = 20, scale = 4)
    private BigDecimal fiftyTwoWeekLow;

    @UpdateTimestamp
    @Column(name = "last_updated_at", nullable = false)
    private OffsetDateTime lastUpdatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}