package com.iiht.StockMarket.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StockPriceIndexDTO {

	
	private CompanyDetailsDTO companyDto;

	
	private List<StockPriceDetailsDTO> stockPriceList;

	
	private Double maxStockPrice;

	
	private Double minStockPrice;

	
	private Double avgStockPrice;

	//--------------------------------------------------------------------------------------------------------------------------
	public StockPriceIndexDTO() {
		super();
	}
	public StockPriceIndexDTO(@NotNull CompanyDetailsDTO companyDto,
			@NotNull List<@NotBlank StockPriceDetailsDTO> stockPriceList,
			@NotNull @Digits(integer = 10, fraction = 2, message = "Stock Price must have precision 10 and factional part of 2 decimals") Double maxStockPrice,
			@NotNull @Digits(integer = 10, fraction = 2, message = "Stock Price must have precision 10 and factional part of 2 decimals") Double minStockPrice,
			@NotNull @Digits(integer = 10, fraction = 2, message = "Stock Price must have precision 10 and factional part of 2 decimals") Double avgStockPrice) {
		super();
		this.companyDto = companyDto;
		this.stockPriceList = stockPriceList;
		this.maxStockPrice = maxStockPrice;
		this.minStockPrice = minStockPrice;
		this.avgStockPrice = avgStockPrice;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyDto() {
		return companyDto;
	}
	public void setCompanyDto(CompanyDetailsDTO companyDto) {
		this.companyDto = companyDto;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockPriceList() {
		return stockPriceList;
	}
	public void setStockPriceList(List<StockPriceDetailsDTO> stockPriceList) {
		this.stockPriceList = stockPriceList;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getMaxStockPrice() {
		return maxStockPrice;
	}
	public void setMaxStockPrice(Double maxStockPrice) {
		this.maxStockPrice = maxStockPrice;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getMinStockPrice() {
		return minStockPrice;
	}
	public void setMinStockPrice(Double minStockPrice) {
		this.minStockPrice = minStockPrice;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getAvgStockPrice() {
		return avgStockPrice;
	}
	public void setAvgStockPrice(Double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}
}