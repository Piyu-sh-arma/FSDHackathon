package com.iiht.StockMarket.inpectors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.iiht.StockMarket.dto.StockPriceDetailsDTO;

public class InspactStockPrice {
    private List<String> errors;

    public boolean checkData(StockPriceDetailsDTO sDetailsDTO) {
        errors = new ArrayList<>();
        if (null == sDetailsDTO)
            errors.add("Stock Price details are required");
        else {
            if (null == sDetailsDTO.getCompanyCode() || sDetailsDTO.getCompanyCode() == 0)
                errors.add("Company code is required and can't be 0");

            if (null != sDetailsDTO.getStockPriceDate()) {
                LocalDate sDate = sDetailsDTO.getStockPriceDate();
                if (sDate.isAfter(LocalDate.now()))
                    errors.add("Stock price date can't exceed current date.");
                if (null != sDetailsDTO.getStockPriceTime()) {
                    LocalTime sTime = sDetailsDTO.getStockPriceTime();
                    if (sDate.isEqual(LocalDate.now()) && sTime.isAfter(LocalTime.now()))
                        errors.add("Stock price time can't exceed current time.");

                } else
                    errors.add("Stock price time is required.");

            } else
                errors.add("Stock price date is required.");

            Double stockPrice = sDetailsDTO.getCurrentStockPrice();
            if (null == stockPrice || stockPrice != 0) {
                String temp[] = String.valueOf(sDetailsDTO.getCurrentStockPrice()).split("\\.");
                String precision = temp[0].replace(",", "");
                String scale = temp[1];
                if (precision.length() > 10 || scale.length() > 2)
                    errors.add("Stock price precision should be 10 and scale 2.");
            } else
                errors.add("Stock price is required and can't be 0");

        }
        return !(errors.size() > 0);
    }

    public List<String> getErrors() {
        return errors;
    }

}
