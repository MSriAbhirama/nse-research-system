package com.nse.ai.company_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nse.ai.company_service.dto.v1.CompanyCreateRequest;
import com.nse.ai.company_service.dto.v1.CompanyResponse;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusRequest;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusResponse;
import com.nse.ai.company_service.exception.CompanyNotFoundException;
import com.nse.ai.company_service.model.CompanyModel;
import com.nse.ai.company_service.model.CompanyStatus;
import com.nse.ai.company_service.repo.CompanyRepo;

@Service
public class CompanyService {
    private final CompanyRepo companyRepo;

    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }
    
    public CompanyResponse createCompany(CompanyCreateRequest companyCreateRequest) {
        CompanyModel companyModel = new CompanyModel();
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(companyCreateRequest, companyModel);
        companyModel.setStatus(CompanyStatus.ACTIVE);
        System.out.println("Creating company: " +companyModel.getStatus()+" " + companyModel.getCompanyName() + " " + companyModel.getSymbol() + " " + companyModel.getIsin() + " " + companyModel.getSector() + " " + companyModel.getIndustry() + " " + companyModel.getSeries() + " " + companyModel.getListingDate());
        CompanyModel savedCompany = companyRepo.save(companyModel);
        BeanUtils.copyProperties(savedCompany, companyResponse);
        System.out.println("Created company: " +companyResponse.getStatus()+" " + companyResponse.getCompanyName() + " " + companyResponse.getSymbol() + " " + companyResponse.getIsin() + " " + companyResponse.getSector() + " " + companyResponse.getIndustry() + " " + companyResponse.getSeries() + " " + companyResponse.getListingDate());
        return companyResponse;
    }

    public CompanyResponse getCompanyById(Long companyId) {
            CompanyModel companyModel = companyRepo.findById(companyId) .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + companyId));
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(companyModel, companyResponse);
            return companyResponse;
    }

    public CompanyResponse getCompanyBySymbol(String symbol) {

        CompanyModel company = companyRepo
                .findBySymbol(symbol)
                .orElseThrow(() ->
                        new CompanyNotFoundException(
                                "Company with symbol " + symbol + " not found"
                        ));

        CompanyResponse response = new CompanyResponse();

        BeanUtils.copyProperties(company, response);

        return response;
    }

    public CompanyResponse getCompanyByIsin(String isin) {
            CompanyModel companyModel = companyRepo.findByIsin(isin).orElseThrow(() -> new CompanyNotFoundException("Company not found with ISIN: " + isin));
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(companyModel, companyResponse);
            return companyResponse;
    }

    public Page<CompanyResponse> getCompanies(Pageable pageable) {

        Page<CompanyModel> companies =
                companyRepo.findAll(pageable);

        return companies.map(company -> {
            CompanyResponse response = new CompanyResponse();
            BeanUtils.copyProperties(company, response);
            return response;
        });
    }

   public Page<CompanyResponse> searchCompanies(
        String query,
        Pageable pageable) {

        Page<CompanyModel> companies =
                companyRepo.search(query, pageable);

        return companies.map(company -> {
            CompanyResponse response =
                    new CompanyResponse();

            BeanUtils.copyProperties(company, response);

            return response;
        });
    }
    public CompanyUpdateStatusResponse updateCompanyStatus(
        Long id,
        CompanyUpdateStatusRequest request) {

        CompanyModel company = companyRepo.findById(id)
                .orElseThrow(() ->
                        new CompanyNotFoundException(
                                "Company not found: " + id
                        ));

        company.setStatus(request.getStatus());

        CompanyModel saved = companyRepo.save(company);

        CompanyUpdateStatusResponse response =
                new CompanyUpdateStatusResponse();

        BeanUtils.copyProperties(saved, response);

        return response;
    }
}
