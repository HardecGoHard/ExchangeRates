package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.CurrencyDto;
import com.alfa.exchangerate.exception.ExchangeRateException;
import com.alfa.exchangerate.service.api.OpenExchangeRatesClient;
import com.alfa.exchangerate.util.ExchangeCalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

@Service
public class ExchangeRatesService {
    protected static final Logger LOGGER = Logger.getLogger(ExchangeRateException.class.getName());
    private final OpenExchangeRatesClient apiExchangeRates;

    @Value("${open-exchange.api.app_id}")
    private String appId;
    @Value("${open-exchange.api.base-currency}")
    private String baseCurrencyCode;

    @Autowired
    public ExchangeRatesService(OpenExchangeRatesClient apiExchangeRates) {
        this.apiExchangeRates = apiExchangeRates;
    }

    public CurrencyDto getBaseCurrencyRateByDate(String date, String currencyCode) {
        LOGGER.info(String.format("getting currency %s rate by %s from OpenExchangeRate.org", currencyCode, date));
        CurrencyDto currency = apiExchangeRates.getBaseCurrencyRateByDate(
                date + ".json",
                appId,
                baseCurrencyCode,
                currencyCode);
        if (currency.getRates().isEmpty()) {
            LOGGER.warning(String.format("currency %s not found by", currencyCode));
            throw new ExchangeRateException("Курс к данной валюте не найден, проверьте правильность запроса");
        }
        return currency;
    }

    public Boolean isExchangeRateIsInc(String currencyCode) {
        LOGGER.info("comparison of the current exchange rate with the past");
        Double currentRate = getBaseCurrencyRateByDate(getDateFormat(0), currencyCode)
                .getRates().get(currencyCode);
        Double yesterdayRate = getBaseCurrencyRateByDate(getDateFormat(1), currencyCode)
                .getRates().get(currencyCode);
        Double currentBaseCurrency_RubRate = getBaseCurrencyRateByDate(getDateFormat(0),
                "RUB").getRates().get("RUB");
        Double yesterdayBaseCurrency_RubRate = getBaseCurrencyRateByDate(getDateFormat(1),
                "RUB").getRates().get("RUB");
        return ExchangeCalculateUtil.getRUBRate(currentBaseCurrency_RubRate, currentRate)
                > ExchangeCalculateUtil.getRUBRate(yesterdayBaseCurrency_RubRate, yesterdayRate);
    }

    // amountOfDay - allows you to get the exchange rate for the past days, numberOfDaysAgo = 0 - current date rate
    private String getDateFormat(int numberOfDaysAgo) {
        if (numberOfDaysAgo > 0) {
            numberOfDaysAgo = Math.negateExact(numberOfDaysAgo);
        }
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, numberOfDaysAgo);
        return dateFormat.format(cal.getTime());
    }
}
