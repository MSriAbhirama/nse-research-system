package com.nse.ai.data_ingestion_service.contorller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nse.ai.data_ingestion_service.dto.v1.CompanyIngestionRequest;
import com.nse.ai.data_ingestion_service.dto.v1.CompanyResponse;
import com.nse.ai.data_ingestion_service.service.DataIngestionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/data-ingestion")
public class dataIngestionController {

    private final DataIngestionService dataIngestionService;

    public dataIngestionController(
            DataIngestionService dataIngestionService) {
        this.dataIngestionService = dataIngestionService;
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponse> ingestCompany(
            @RequestBody CompanyIngestionRequest request) {

        CompanyResponse response =
                dataIngestionService.ingestCompany(request);

        return ResponseEntity.ok(response);
    }
}
