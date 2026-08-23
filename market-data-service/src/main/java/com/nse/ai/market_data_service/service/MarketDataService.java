package com.nse.ai.market_data_service.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nse.ai.market_data_service.dto.v1.MarketCandleRequest;
import com.nse.ai.market_data_service.dto.v1.MarketCandleResponse;
import com.nse.ai.market_data_service.dto.v1.MarketQuoteRequest;
import com.nse.ai.market_data_service.dto.v1.MarketQuoteResponse;
import com.nse.ai.market_data_service.model.MarketCandleModel;
import com.nse.ai.market_data_service.model.MarketQuoteModel;
import com.nse.ai.market_data_service.repo.MarketCandleRepo;
import com.nse.ai.market_data_service.repo.MarketQuoteRepo;

@Service
public class MarketDataService {

    private final MarketQuoteRepo marketQuoteRepo;
    private final MarketCandleRepo marketCandleRepo;

    public MarketDataService(
            MarketQuoteRepo marketQuoteRepo,
            MarketCandleRepo marketCandleRepo) {

        this.marketQuoteRepo = marketQuoteRepo;
        this.marketCandleRepo = marketCandleRepo;
    }

    // =========================
    // MARKET QUOTE
    // =========================

    public MarketQuoteResponse saveOrUpdateQuote(
            MarketQuoteRequest request) {

        MarketQuoteModel quote = marketQuoteRepo
                .findBySymbol(request.getSymbol())
                .orElse(new MarketQuoteModel());

        BeanUtils.copyProperties(request, quote);

        if (quote.getLastUpdatedAt() == null) {
            quote.setLastUpdatedAt(OffsetDateTime.now());
        }

        MarketQuoteModel saved =
                marketQuoteRepo.save(quote);

        MarketQuoteResponse response =
                new MarketQuoteResponse();

        BeanUtils.copyProperties(saved, response);

        return response;
    }

    public MarketQuoteResponse getQuoteBySymbol(
            String symbol) {

        MarketQuoteModel quote = marketQuoteRepo
                .findBySymbol(symbol)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Market quote not found for symbol: "
                                        + symbol
                        )
                );

        MarketQuoteResponse response =
                new MarketQuoteResponse();

        BeanUtils.copyProperties(quote, response);

        return response;
    }

    // =========================
    // MARKET CANDLE
    // =========================

    public MarketCandleResponse createCandle(
            MarketCandleRequest request) {

        MarketCandleModel candle =
                new MarketCandleModel();

        BeanUtils.copyProperties(request, candle);

        MarketCandleModel saved =
                marketCandleRepo.save(candle);

        MarketCandleResponse response =
                new MarketCandleResponse();

        BeanUtils.copyProperties(saved, response);

        return response;
    }

    public List<MarketCandleResponse> getCandles(
            String symbol,
            String interval) {

        return marketCandleRepo
                .findBySymbolAndIntervalOrderByCandleTimestampAsc(
                        symbol,
                        interval
                )
                .stream()
                .map(candle -> {

                    MarketCandleResponse response =
                            new MarketCandleResponse();

                    BeanUtils.copyProperties(
                            candle,
                            response
                    );

                    return response;
                })
                .toList();
    }

    public List<MarketCandleResponse> getCandlesBetween(
            String symbol,
            String interval,
            OffsetDateTime from,
            OffsetDateTime to) {

        return marketCandleRepo
                .findBySymbolAndIntervalAndCandleTimestampBetweenOrderByCandleTimestampAsc(
                        symbol,
                        interval,
                        from,
                        to
                )
                .stream()
                .map(candle -> {

                    MarketCandleResponse response =
                            new MarketCandleResponse();

                    BeanUtils.copyProperties(
                            candle,
                            response
                    );

                    return response;
                })
                .toList();
    }
}