package com.nse.ai.company_service.dto.v1;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.nse.ai.company_service.model.CompanyStatus;

import lombok.Data;

@Data
public class CompanyResponse {

    private Long id;
    private String companyName;
    private String symbol;
    private String isin;
    private CompanyStatus status;
    private String sector;
    private String industry;
    private String series;
    private LocalDate listingDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}