package com.nse.ai.company_service.dto.v1;

import lombok.Data;

@Data
public class ErrorResponse {
    private int errorCode;
    private String message;

    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
