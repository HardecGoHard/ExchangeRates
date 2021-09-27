package com.alfa.exchangerate.service.api;


import com.alfa.exchangerate.dto.CurrencyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "api-open-exchange-rates", url = "${open-exchange.api.url}")
public interface OpenExchangeRatesClient {
    @GetMapping("${open-exchange.api.latest}")
    CurrencyDto getCurrentUSDRate(@RequestParam("app_id") String appId, @RequestParam("symbols") String currencyCode);

    @GetMapping("${open-exchange.api.historical}/{date}")
    CurrencyDto getUSDRateByDate(@PathVariable String date, @RequestParam("app_id") String appId,
                                 @RequestParam("base")String base,
                                 @RequestParam("symbols") String currencyCode);
}
