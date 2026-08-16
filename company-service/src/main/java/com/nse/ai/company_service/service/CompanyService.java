package com.nse.ai.company_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nse.ai.company_service.dto.v1.CompanyCreateRequest;
import com.nse.ai.company_service.dto.v1.CompanyResponse;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusRequest;
import com.nse.ai.company_service.dto.v1.CompanyUpdateStatusResponse;
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
        CompanyModel savedCompany = companyRepo.save(companyModel);
        BeanUtils.copyProperties(savedCompany, companyResponse);
        System.out.println("Company created with ID: " + savedCompany.getId());
        System.out.println(companyModel.getCompanyName() + " " + companyModel.getSymbol() + " " + companyModel.getIsin() + " " + companyModel.getSector() + " " + companyModel.getIndustry() + " " + companyModel.getSeries() + " " + companyModel.getListingDate());
        return companyResponse;
    }

    public CompanyResponse getCompanyById(Long companyId) {
        try{
            CompanyModel companyModel = companyRepo.findById(companyId) .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(companyModel, companyResponse);
            return companyResponse;
        }catch(Exception e){
            throw new RuntimeException("Error fetching company by ID", e);
        }
    }

    public CompanyResponse getCompanyBySymbol(String symbol) {

        CompanyModel company = companyRepo
                .findBySymbol(symbol)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company with symbol " + symbol + " not found"
                        ));

        CompanyResponse response = new CompanyResponse();

        BeanUtils.copyProperties(company, response);

        return response;
    }

    public CompanyResponse getCompanyByIsin(String isin) {
        try{
            CompanyModel companyModel = companyRepo.findAll().stream()
                    .filter(company -> company.getIsin().equalsIgnoreCase(isin))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Company not found with ISIN: " + isin));
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(companyModel, companyResponse);
            return companyResponse;
        }catch(Exception e){
            throw new RuntimeException("Error fetching company by ISIN", e);
        }
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
                        new RuntimeException(
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
