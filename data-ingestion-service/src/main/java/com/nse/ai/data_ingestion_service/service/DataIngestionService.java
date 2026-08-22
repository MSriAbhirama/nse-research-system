package com.nse.ai.data_ingestion_service.service;

import org.springframework.stereotype.Service;

import com.nse.ai.data_ingestion_service.client.CompanyServiceClient;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyIngestionRequest;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;

@Service
public class DataIngestionService {

    private final CompanyServiceClient companyServiceClient;

    public DataIngestionService(CompanyServiceClient companyServiceClient) {
        this.companyServiceClient = companyServiceClient;
    }

    public CompanyResponse ingestCompany(CompanyIngestionRequest request) {
        return companyServiceClient.createCompany(request);
    }

}
