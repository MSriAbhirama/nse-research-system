package com.nse.ai.market_data_service.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nse.ai.market_data_service.model.MarketQuoteModel;

public interface MarketQuoteRepo
        extends JpaRepository<MarketQuoteModel, Long> {

    Optional<MarketQuoteModel> findBySymbol(String symbol);

}
