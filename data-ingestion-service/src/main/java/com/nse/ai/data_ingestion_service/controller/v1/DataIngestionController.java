package com.nse.ai.data_ingestion_service.controller.v1;

import com.nse.ai.data_ingestion_service.service.MarketQuoteProducer;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nse.ai.data_ingestion_service.dto.v1.CompanyIngestionRequest;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;
import com.nse.ai.data_ingestion_service.dto.v1.kafka.MarketQuoteEvent;
import com.nse.ai.data_ingestion_service.service.DataIngestionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/data-ingestion")
public class DataIngestionController{

    private final MarketQuoteProducer marketQuoteProducer;
    private final DataIngestionService dataIngestionService;

    public DataIngestionController(
            DataIngestionService dataIngestionService, MarketQuoteProducer marketQuoteProducer) {
        this.dataIngestionService = dataIngestionService;
        this.marketQuoteProducer = marketQuoteProducer;
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponse> ingestCompany(
            @RequestBody CompanyIngestionRequest request) {

        CompanyResponse response =
                dataIngestionService.ingestCompany(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/market-quotes")
    public CompletableFuture<ResponseEntity<String>> publishMarketQuote(
            @RequestBody MarketQuoteEvent event) {

        return marketQuoteProducer.publish(event)
        .thenApply(result->{
            RecordMetadata metadata = result.getRecordMetadata();

            return ResponseEntity.accepted().body(
                "Market quote published to Kafka topic: " + metadata.topic() +
                ", partition: " + metadata.partition() +
                ", offset: " + metadata.offset()
            );
        }).exceptionally(ex->{
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Failed to publish market quote to Kafka: " + ex.getMessage());
        });

    }
}
