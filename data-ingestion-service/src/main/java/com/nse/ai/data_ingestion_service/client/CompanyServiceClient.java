package com.nse.ai.data_ingestion_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nse.ai.data_ingestion_service.dto.v1.CompanyIngestionRequest;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;

@FeignClient(name = "company-service", url = "http://localhost:8080")
public interface CompanyServiceClient {

    @PostMapping("/api/v1/companies")
    CompanyResponse createCompany(@RequestBody CompanyIngestionRequest request);

    @GetMapping("/api/v1/companies/symbol/{symbol}")
    CompanyResponse getCompanyBySymbol(@PathVariable("symbol") String symbol);
}
