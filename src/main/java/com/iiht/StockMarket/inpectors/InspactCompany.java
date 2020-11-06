package com.iiht.StockMarket.inpectors;

import java.util.ArrayList;
import java.util.List;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;

public class InspactCompany {
    private List<String> errors;

    public boolean isValid(CompanyDetailsDTO cDetailsDTO) {
        errors = new ArrayList<>();
        if (null == cDetailsDTO)
            errors.add("Comapny details are required");
        else {
            if (null == cDetailsDTO.getCompanyCode() || cDetailsDTO.getCompanyCode() == 0)
                errors.add("Company code is required and can't be 0");

            if (null != cDetailsDTO.getCompanyName()) {
                int fieldlen = cDetailsDTO.getCompanyName().trim().length();
                if (fieldlen != 0) {
                    if (fieldlen < 3 || fieldlen > 100)
                        errors.add("Company Name length should be between 3 to 100 Chars.");
                } else
                    errors.add("Company Name can't be blank.");
            } else
                errors.add("Company Name is required.");

            if (null != cDetailsDTO.getCompanyCEO()) {
                int fieldlen = cDetailsDTO.getCompanyCEO().trim().length();
                if (fieldlen != 0) {
                    if (fieldlen < 5 || fieldlen > 100)
                        errors.add("Company CEO name length should be between 5 to 100 Chars.");
                } else
                    errors.add("Company CEO name can't be blank.");
            } else
                errors.add("Company CEO name is required.");

            if (null != cDetailsDTO.getBoardOfDirectors()) {
                int fieldlen = cDetailsDTO.getBoardOfDirectors().trim().length();
                if (fieldlen != 0) {
                    if (fieldlen < 5 || fieldlen > 200)
                        errors.add("Company Board Of Directors length should be between 5 to 200 Chars.");
                } else
                    errors.add("Company Board Of Directors can't be blank.");
            } else
                errors.add("Company Board Of Directors is required.");

            if (null != cDetailsDTO.getCompanyProfile()) {
                int fieldlen = cDetailsDTO.getCompanyProfile().trim().length();
                if (fieldlen != 0) {
                    if (fieldlen < 5 || fieldlen > 255)
                        errors.add("Company profile should be between 5 to 255 Chars.");
                } else
                    errors.add("Company profile can't be blank.");
            } else
                errors.add("Company profile is required.");

            if (null != cDetailsDTO.getStockExchange()) {
                int fieldlen = cDetailsDTO.getStockExchange().trim().length();
                if (fieldlen != 0) {
                    if (fieldlen < 3 || fieldlen > 100)
                        errors.add("Stock Exchange should be between 3 to 100 Chars.");
                } else
                    errors.add("Stock Exchange can't be blank.");
            } else
                errors.add("Stock Exchange is required.");

            Double turnOver = cDetailsDTO.getTurnover();
            if (null == turnOver || turnOver != 0) {
                String temp[] = String.valueOf(cDetailsDTO.getTurnover()).split("\\.");
                String precision = temp[0].replace(",", "");
                String scale = temp[1];
                if (precision.length() > 10 || scale.length() > 2)
                    errors.add("Company turnover precision should be 10 and scale 2.");
            } else
                errors.add("Company turnover is required and can't be 0");

        }
        return !(errors.size() > 0);
    }

    public List<String> getErrors() {
        return errors;
    }

}
