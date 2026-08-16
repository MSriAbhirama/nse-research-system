package com.nse.ai.company_service.dto.v1;

import java.time.OffsetDateTime;

import com.nse.ai.company_service.model.CompanyStatus;

import lombok.Data;

@Data
public class CompanyUpdateStatusResponse {

    private Long id;
    private String symbol;
    private String name;
    private CompanyStatus status;
    private OffsetDateTime updatedAt;
}
