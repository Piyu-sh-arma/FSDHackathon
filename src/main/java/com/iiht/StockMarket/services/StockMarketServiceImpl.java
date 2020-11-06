package com.iiht.StockMarket.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceIndexDTO;
import com.iiht.StockMarket.exception.InvalidStockException;
import com.iiht.StockMarket.model.CompanyDetails;
import com.iiht.StockMarket.model.StockPriceDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;
import com.iiht.StockMarket.inpectors.InspactStockPrice;

@Service
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

    @Autowired
    private StockPriceRepository stockRepository;

    @Autowired
    private CompanyInfoRepository companyRepository;

    // ----------------------------------------------------------------------------
    public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) {
        InspactStockPrice validator = new InspactStockPrice();
        if (!validator.checkData(stockPriceDetailsDTO))
            throw new InvalidStockException(validator.getErrors().toString());
        CompanyDetails companyDetails = companyRepository.findCompanyDetailsById(stockPriceDetailsDTO.getCompanyCode());
        if (null == companyDetails)
            throw new InvalidStockException("Company Code - " + stockPriceDetailsDTO.getCompanyCode() + " not found.");

        stockRepository.save(StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO));
        return stockPriceDetailsDTO;
    }

    // ----------------------------------------------------------------------------
    public List<StockPriceDetailsDTO> deleteStock(Long companyCode) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");
        List<StockPriceDetailsDTO> stPriceDetailsDTOs = StockMarketUtility
                .convertToStockPriceDetailsDtoList(stockRepository.findStockByCompanyCode(companyCode));
        stockRepository.deleteStockByCompanyCode(companyCode);
        return stPriceDetailsDTOs;
    }

    // ----------------------------------------------------------------------------
    public List<StockPriceDetailsDTO> getStockByCode(Long companyCode) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");
        List<StockPriceDetailsDTO> stPriceDetailsDTOs = StockMarketUtility
                .convertToStockPriceDetailsDtoList(stockRepository.findStockByCompanyCode(companyCode));
        return stPriceDetailsDTOs;
    };

    // ----------------------------------------------------------------------------
    public StockPriceDetailsDTO getStockPriceDetailsDTO(StockPriceDetails stockDetails) {
        return StockMarketUtility.convertToStockPriceDetailsDTO(stockDetails);
    }

    // ----------------------------------------------------------------------------
    public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");        
        return stockRepository.findMaxStockPrice(companyCode, startDate, endDate);
    }

    public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");        
        return stockRepository.findAvgStockPrice(companyCode, startDate, endDate);
    }

    public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");
        return stockRepository.findMinStockPrice(companyCode, startDate, endDate);
    }

    public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate) {
        if (null == companyCode)
            throw new InvalidStockException("Company code can't be null");
        CompanyDetails companyDetails = companyRepository.findCompanyDetailsById(companyCode);
        if (null == companyDetails)
            throw new InvalidStockException("Company Code - " + companyCode + " not found.");

        CompanyDetailsDTO companyDetailsDTO = StockMarketUtility.convertToCompanyDetailsDTO(companyDetails);
        List<StockPriceDetailsDTO> stPriceDetailsDTOs = this.getStockByCode(companyCode);
        Double maxStockPrice = this.getMaxStockPrice(companyCode, startDate, endDate);
        Double minStockPrice = this.getMinStockPrice(companyCode, startDate, endDate);
        Double avgStockPrice = this.getAvgStockPrice(companyCode, startDate, endDate);

        return new StockPriceIndexDTO(companyDetailsDTO, stPriceDetailsDTOs, maxStockPrice, minStockPrice,
                avgStockPrice);
    }
}