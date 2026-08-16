package com.nse.ai.company_service.dto.v1;

import com.nse.ai.company_service.model.CompanyStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyUpdateStatusRequest {

    @NotNull
    private CompanyStatus status;

    // getters/setters
}