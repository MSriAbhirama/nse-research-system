package com.nse.ai.market_data_service.repo;


import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nse.ai.market_data_service.model.MarketCandleModel;

public interface MarketCandleRepo
        extends JpaRepository<MarketCandleModel, Long> {

    List<MarketCandleModel> findBySymbolAndIntervalOrderByCandleTimestampAsc(
            String symbol,
            String interval
    );

    List<MarketCandleModel>
    findBySymbolAndIntervalAndCandleTimestampBetweenOrderByCandleTimestampAsc(
            String symbol,
            String interval,
            OffsetDateTime from,
            OffsetDateTime to
    );
}
