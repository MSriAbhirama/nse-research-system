package com.nse.ai.company_service.controller.v1;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nse.ai.company_service.dto.v1.CompanyCreateRequest;
import com.nse.ai.company_service.dto.v1.CompanyResponse;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusRequest;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusResponse;
import com.nse.ai.company_service.service.CompanyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestBody CompanyCreateRequest request) {

        CompanyResponse response =
                companyService.createCompany(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getCompanies(
            @PageableDefault(size = 20, sort = "companyName")
            Pageable pageable) {

        return ResponseEntity.ok(
                companyService.getCompanies(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                companyService.getCompanyById(id)
        );
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<CompanyResponse> getCompanyBySymbol(
            @PathVariable String symbol) {

        return ResponseEntity.ok(
                companyService.getCompanyBySymbol(symbol)
        );
    }

    @GetMapping("/isin/{isin}")
    public ResponseEntity<CompanyResponse> getCompanyByIsin(
            @PathVariable String isin) {

        return ResponseEntity.ok(
                companyService.getCompanyByIsin(isin)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<Page<CompanyResponse>> searchCompanies(
            @RequestParam String q,
            @PageableDefault(size = 20)
            Pageable pageable) {

        return ResponseEntity.ok(
                companyService.searchCompanies(q, pageable)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CompanyUpdateStatusResponse> updateCompanyStatus(
            @PathVariable Long id,
            @RequestBody CompanyUpdateStatusRequest request) {

        return ResponseEntity.ok(
                companyService.updateCompanyStatus(id, request)
        );
    }
}