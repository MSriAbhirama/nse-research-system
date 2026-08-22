package com.nse.ai.company_service.exception;

import com.nse.ai.company_service.dto.v1.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(CompanyNotFoundException.class)
public ResponseEntity<ErrorResponse> handleCompanyNotFoundException(
        CompanyNotFoundException ex) {

    ErrorResponse errorResponse =
            new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
}

}
