package com.iiht.StockMarket.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.exception.CompanyNotFoundException;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.model.CompanyDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;
import com.iiht.StockMarket.inpectors.InspactCompany;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoRepository repository;

    @Autowired
    private StockPriceRepository stockRepository;

    public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) {
        InspactCompany validator = new InspactCompany();
        if (!validator.isValid(companyDetailsDTO))
            throw new InvalidCompanyException(validator.getErrors().toString());

        CompanyDetails companyDetails = repository.findCompanyDetailsById(companyDetailsDTO.getCompanyCode());
        if (null != companyDetails)
            throw new InvalidCompanyException(
                    "Company Code - " + companyDetailsDTO.getCompanyCode() + " already exist.");

        repository.save(StockMarketUtility.convertToCompanyDetails(companyDetailsDTO));
        return companyDetailsDTO;
    };

    // ----------------------------------------------------------------------------
    public CompanyDetailsDTO deleteCompany(Long companyCode) {
        if (null == companyCode)
            throw new InvalidCompanyException("Company code can't be null");
        CompanyDetails companyDetails = repository.findCompanyDetailsById(companyCode);
        if (null == companyDetails)
            throw new CompanyNotFoundException("Company Code - " + companyCode + " not found.");
        stockRepository.deleteStockByCompanyCode(companyCode);

        repository.deleteByCompanyCode(companyCode);
        return StockMarketUtility.convertToCompanyDetailsDTO(companyDetails);
    };

    // ----------------------------------------------------------------------------
    public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
        if (null == companyCode)
            throw new InvalidCompanyException("Company code can't be null");
        CompanyDetails companyDetails = repository.findCompanyDetailsById(companyCode);
        if (null == companyDetails)
            throw new CompanyNotFoundException("Company Code - " + companyCode + " not found.");
        return StockMarketUtility.convertToCompanyDetailsDTO(companyDetails);
    };

    // ----------------------------------------------------------------------------
    public List<CompanyDetailsDTO> getAllCompanies() {
        return StockMarketUtility.convertToCompanyDetailsDtoList(repository.findAll());
    }
}