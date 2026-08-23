package com.nse.ai.market_data_service.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
    name = "market_candle",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "symbol",
                "interval",
                "candle_timestamp"
            }
        )
    }
)
@Data
public class MarketCandleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String interval;

    @Column(
        name = "candle_timestamp",
        nullable = false
    )
    private OffsetDateTime candleTimestamp;

    @Column(name = "open_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal openPrice;

    @Column(name = "high_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal highPrice;

    @Column(name = "low_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal lowPrice;

    @Column(name = "close_price", nullable = false, precision = 20, scale = 4)
    private BigDecimal closePrice;

    @Column(nullable = false)
    private Long volume;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}