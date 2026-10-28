package com.nse.ai.data_ingestion_service.service;

import org.springframework.stereotype.Service;

import com.nse.ai.data_ingestion_service.client.CompanyServiceClient;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.LoggerFactory;


@Service 
public class CompanyLookupService {

    private final CompanyServiceClient companyServiceClient;

    public CompanyLookupService(CompanyServiceClient companyServiceClient) {
        this.companyServiceClient = companyServiceClient;
    }

    @Retry (name = "companyServiceRetry")
    public CompanyResponse getCompanyBySymbol(String symbol) {
        LoggerFactory.getLogger(CompanyLookupService.class).info("Fetching company details for symbol: {}", symbol);
        return companyServiceClient.getCompanyBySymbol(symbol);
    }
}