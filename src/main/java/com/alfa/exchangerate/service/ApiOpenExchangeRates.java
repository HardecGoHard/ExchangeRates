package com.alfa.exchangerate.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "api-open-exchange-rates", url = "http://openexchangerates.org/api")
@Component
public interface ApiOpenExchangeRates {
    @GetMapping("/latestd.json")
    Object getUSDRate(@RequestParam("app_id") String appId, @RequestParam("symbols") String currencyCode);
}
