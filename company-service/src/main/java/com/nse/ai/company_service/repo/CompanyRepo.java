package com.nse.ai.company_service.repo;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nse.ai.company_service.model.CompanyModel;

public interface CompanyRepo extends JpaRepository<CompanyModel, Long> {

    Page<CompanyModel> findAll(Pageable pageable);
    Optional<CompanyModel> findBySymbol(String symbol);
    Optional<CompanyModel> findByIsin(String isin);

    @Query("""
    SELECT c
    FROM CompanyModel c
    WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(c.symbol) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(c.isin) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<CompanyModel> search(
            @Param("query") String query,
            Pageable pageable
    );

}
