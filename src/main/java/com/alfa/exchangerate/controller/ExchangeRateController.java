package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.service.ApiOpenExchangeRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ExchangeRateController {
    private final ApiOpenExchangeRates apiOpenExchangeRates;

    @Value("${open-exchange.api.app_id}")
    private String APP_ID;

    public ExchangeRateController(ApiOpenExchangeRates apiOpenExchangeRates) {
        this.apiOpenExchangeRates = apiOpenExchangeRates;
    }

    @GetMapping("/{exchangeId}")
    public Object getExchangeGif(@PathVariable("exchangeId") String exchangeId) {
        return apiOpenExchangeRates.getUSDRate(APP_ID, exchangeId);
    }
}
