package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.dto.CurrencyDto;
import com.alfa.exchangerate.service.ExchangeRatesService;
import com.alfa.exchangerate.util.ExchangeCalculateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@RequestMapping
public class ExchangeRateController {
    private final ExchangeRatesService exchangeRatesService;

    public ExchangeRateController(ExchangeRatesService apiOpenExchangeRates) {
        this.exchangeRatesService = apiOpenExchangeRates;
    }

    @GetMapping("/{currencyId}")
    public String getExchangeGif(@PathVariable("currencyId") String currencyId) {

        return comparingTheCurrentAndYesterdayDay(currencyId);
    }
    private String comparingTheCurrentAndYesterdayDay(String currencyId){
        if (exchangeRatesService.isExchangeRateIsInc(currencyId)){
            return "You are RICH!!!";
        }
        return "Not stonks....";
    }

}
