package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.CurrencyDto;
import com.alfa.exchangerate.exception.ExchangeRateException;
import com.alfa.exchangerate.service.api.OpenExchangeRatesClient;
import com.alfa.exchangerate.util.DateFormaterUtil;
import com.alfa.exchangerate.util.ExchangeCalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ExchangeRatesService {
    protected static final Logger LOGGER = Logger.getLogger(ExchangeRateException.class.getName());
    private final OpenExchangeRatesClient openExchangeRatesClient;

    private String appId;
    private String baseCurrencyCode;

    @Autowired
    public ExchangeRatesService(OpenExchangeRatesClient openExchangeRatesClient,
                                @Value("${open-exchange.api.app_id}") String appId,
                                @Value("${open-exchange.api.base-currency}") String baseCurrencyCode) {
        this.openExchangeRatesClient = openExchangeRatesClient;
        this.appId = appId;
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public CurrencyDto getUSDRateByDate(String date, String currencyCode) {
        LOGGER.info(String.format("getting currency %s rate by %s from OpenExchangeRate.org", currencyCode, date));
        CurrencyDto currency = openExchangeRatesClient.getUSDRateByDate(
                date + ".json", appId, currencyCode);
        checkCurrencyRate(currency, currencyCode);
        return currency;
    }

    private void checkCurrencyRate(CurrencyDto currency, String currencyCode) {
        if (currency.getRates().get(currencyCode) == null) {
            LOGGER.warning(String.format("currency %s not found", currencyCode));
            throw new ExchangeRateException("Курс к данной валюте не найден, проверьте правильность запроса");
        }
    }

    public Boolean isExchangeRateIsInc(String currencyCode) {
        if (currencyCode.equals(baseCurrencyCode)){
            return false;
        }
        LOGGER.info("comparison of the current exchange rate with the past");
        Double currentRate = getUSDRateByDate(DateFormaterUtil.getPastDate(0), currencyCode)
                .getRates().get(currencyCode);
        Double yesterdayRate = getUSDRateByDate(DateFormaterUtil.getPastDate(1), currencyCode)
                .getRates().get(currencyCode);
        Double currentBaseCurrency_RubRate = getUSDRateByDate(DateFormaterUtil.getPastDate(0),
                baseCurrencyCode).getRates().get(baseCurrencyCode);
        Double yesterdayBaseCurrency_RubRate = getUSDRateByDate(DateFormaterUtil.getPastDate(1),
                baseCurrencyCode).getRates().get(baseCurrencyCode);
        return ExchangeCalculateUtil.getRUBRate(currentBaseCurrency_RubRate, currentRate)
                > ExchangeCalculateUtil.getRUBRate(yesterdayBaseCurrency_RubRate, yesterdayRate);
    }

    /*  openexchangerates.org API does not provide base currency change on free account

    public CurrencyDto getBaseCurrencyRateByDate(String date, String currencyCode) {
        LOGGER.info(String.format("getting currency %s rate by %s from OpenExchangeRate.org", currencyCode, date));
        CurrencyDto currency = apiExchangeRates.getBaseCurrencyRateByDate(
                date + ".json", appId, baseCurrencyCode, currencyCode);
        checkCurrencyRate(currency,currencyCode);
        return currency;
    }
     */
}
