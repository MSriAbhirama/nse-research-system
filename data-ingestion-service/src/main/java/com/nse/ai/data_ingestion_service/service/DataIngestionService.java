package com.nse.ai.data_ingestion_service.service;

import org.springframework.stereotype.Service;

import com.nse.ai.data_ingestion_service.client.CompanyServiceClient;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyIngestionRequest;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;

import feign.FeignException.FeignClientException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class DataIngestionService {

    private final CompanyServiceClient companyServiceClient;

    public DataIngestionService(CompanyServiceClient companyServiceClient) {
        this.companyServiceClient = companyServiceClient;
    }

    @CircuitBreaker(name = "companyService")
    public CompanyResponse ingestCompany(CompanyIngestionRequest request) {
        try{
            return companyServiceClient.getCompanyBySymbol(request.getSymbol());
        } catch(FeignClientException.NotFound e){
            return companyServiceClient.createCompany(request);
        }
    }

}
