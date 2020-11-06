package com.iiht.StockMarket.dto;

public class CompanyDetailsDTO {

    private Long companyCode;

    private String stockExchange;

    private String companyName;

    private String companyCEO;

    private Double turnover;

    private String boardOfDirectors;

    private String companyProfile;

    // ---------------------------------------------------------------------------------------------------------------------------------
    public CompanyDetailsDTO() {
        super();
    }

    public CompanyDetailsDTO(Long companyCode, String stockExchange, String companyName, String companyCEO,
            Double turnover, String boardOfDirectors, String companyProfile) {
        super();
        this.companyCode = companyCode;
        this.stockExchange = stockExchange;
        this.companyName = companyName;
        this.companyCEO = companyCEO;
        this.turnover = turnover;
        this.boardOfDirectors = boardOfDirectors;
        this.companyProfile = companyProfile;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public Long getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Long companyCode) {
        this.companyCode = companyCode;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public String getCompanyCEO() {
        return companyCEO;
    }

    public void setCompanyCEO(String companyCEO) {
        this.companyCEO = companyCEO;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public String getBoardOfDirectors() {
        return boardOfDirectors;
    }

    public void setBoardOfDirectors(String boardOfDirectors) {
        this.boardOfDirectors = boardOfDirectors;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------
    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }
}