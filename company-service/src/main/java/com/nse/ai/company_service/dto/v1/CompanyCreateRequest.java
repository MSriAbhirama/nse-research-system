package com.nse.ai.company_service.dto.v1;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CompanyCreateRequest {
    private String companyName;
    private String symbol; 
    private String isin;
    private String sector;
    private String industry;
    private String series;
    private LocalDate listingDate;
}
