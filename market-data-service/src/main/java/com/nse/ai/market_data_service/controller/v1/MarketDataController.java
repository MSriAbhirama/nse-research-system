package com.nse.ai.market_data_service.controller.v1;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nse.ai.market_data_service.dto.v1.MarketCandleRequest;
import com.nse.ai.market_data_service.dto.v1.MarketCandleResponse;
import com.nse.ai.market_data_service.dto.v1.MarketQuoteRequest;
import com.nse.ai.market_data_service.dto.v1.MarketQuoteResponse;
import com.nse.ai.market_data_service.service.MarketDataService;

@RestController
@RequestMapping("/api/v1/market-data")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(
            MarketDataService marketDataService) {

        this.marketDataService = marketDataService;
    }

    // =========================
    // QUOTES
    // =========================

    @PostMapping("/quotes")
    public ResponseEntity<MarketQuoteResponse> saveOrUpdateQuote(
            @RequestBody MarketQuoteRequest request) {

        return ResponseEntity.ok(
                marketDataService.saveOrUpdateQuote(request)
        );
    }

    @GetMapping("/quotes/{symbol}")
    public ResponseEntity<MarketQuoteResponse> getQuote(
            @PathVariable String symbol) {

        return ResponseEntity.ok(
                marketDataService.getQuoteBySymbol(symbol)
        );
    }

    // =========================
    // CANDLES
    // =========================

    @PostMapping("/candles")
    public ResponseEntity<MarketCandleResponse> createCandle(
            @RequestBody MarketCandleRequest request) {

        return ResponseEntity.ok(
                marketDataService.createCandle(request)
        );
    }

    @GetMapping("/candles/{symbol}")
    public ResponseEntity<List<MarketCandleResponse>> getCandles(
            @PathVariable String symbol,
            @RequestParam String interval) {

        return ResponseEntity.ok(
                marketDataService.getCandles(
                        symbol,
                        interval
                )
        );
    }

    @GetMapping("/candles/{symbol}/range")
    public ResponseEntity<List<MarketCandleResponse>>
    getCandlesBetween(
            @PathVariable String symbol,
            @RequestParam String interval,
            @RequestParam OffsetDateTime from,
            @RequestParam OffsetDateTime to) {

        return ResponseEntity.ok(
                marketDataService.getCandlesBetween(
                        symbol,
                        interval,
                        from,
                        to
                )
        );
    }
}