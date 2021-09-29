package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.CurrencyDto;
import com.alfa.exchangerate.exception.ExchangeRateException;
import com.alfa.exchangerate.service.api.OpenExchangeRatesClient;
import com.alfa.exchangerate.util.DateFormaterUtil;
import com.alfa.exchangerate.util.ExchangeCalculateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


import java.util.List;
import java.util.Map;

public class ExchangeRatesServiceTest {
    private static final String APP_ID = "bf9ac5652f9b4eda9ce42b9022cfe9b6";
    private static final String DATE = "2021-09-28";
    private static final String DATE_WITH_JSON_PREFIX = "2021-09-28.json";
    private static final String CURRENCY_CODE = "EUR";
    private static final String BASE_CURRENCY = "RUB";
    private static final String INCORRECT_CURRENCY = "RBB";

    private static List<CurrencyDto> currencyDtoList;
    private static ExchangeRatesService exchangeRatesService;
    private static OpenExchangeRatesClient openExchangeRatesClientMock;
    private static CurrencyDto testCurrencyDto;

    @BeforeAll
    public static void setUp() {
        testCurrencyDto = new CurrencyDto(
                "RUB",
                Map.of(CURRENCY_CODE, 85.3)
        );
        currencyDtoList = List.of(
                new CurrencyDto(
                        "USD",
                        Map.of(BASE_CURRENCY, 75.3)),
                new CurrencyDto(
                        "USD",
                        Map.of(BASE_CURRENCY, 76.6)),
                new CurrencyDto(
                        "USD",
                        Map.of(CURRENCY_CODE, 0.85)),
                new CurrencyDto(
                        "USD",
                        Map.of(CURRENCY_CODE, 0.83))
        );
        openExchangeRatesClientMock = Mockito.mock(OpenExchangeRatesClient.class);
        exchangeRatesService = new ExchangeRatesService(openExchangeRatesClientMock, APP_ID, BASE_CURRENCY);
    }

    @Test
    public void getUSDByDate() {
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(DATE_WITH_JSON_PREFIX, APP_ID, CURRENCY_CODE))
                .thenReturn(testCurrencyDto);
        assertThat(exchangeRatesService.getUSDRateByDate(DATE, CURRENCY_CODE).equals(testCurrencyDto));
    }

    @Test
    public void getUSDByDate_Should_Throw_ExchangeRateException() {
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(DATE_WITH_JSON_PREFIX, APP_ID, INCORRECT_CURRENCY))
                .thenReturn(testCurrencyDto);
        Assertions.assertThrows(ExchangeRateException.class, () -> {
            exchangeRatesService.getUSDRateByDate(DATE, INCORRECT_CURRENCY);
        });
    }

    @Test
    public void isExchangeRateIsInc() {
        initMocksOfExchangeRateIsIncFunction();
        Double currentRate = exchangeRatesService.getUSDRateByDate(
                DateFormaterUtil.getPastDate(0), CURRENCY_CODE).getRates().get(CURRENCY_CODE);
        Double yesterdayRate = exchangeRatesService.getUSDRateByDate(
                DateFormaterUtil.getPastDate(1), CURRENCY_CODE).getRates().get(CURRENCY_CODE);
        Double currentBaseCurrency_RubRate = exchangeRatesService.getUSDRateByDate(
                DateFormaterUtil.getPastDate(0), BASE_CURRENCY).getRates().get(BASE_CURRENCY);
        Double yesterdayBaseCurrency_RubRate = exchangeRatesService.getUSDRateByDate(
                DateFormaterUtil.getPastDate(1), BASE_CURRENCY).getRates().get(BASE_CURRENCY);

        assertThat(ExchangeCalculateUtil.getRUBRate(currentBaseCurrency_RubRate, currentRate)
                > ExchangeCalculateUtil.getRUBRate(yesterdayBaseCurrency_RubRate, yesterdayRate)).isFalse();
    }

    private void initMocksOfExchangeRateIsIncFunction() {
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(
                DateFormaterUtil.getPastDate(0) + ".json",
                APP_ID, BASE_CURRENCY)).thenReturn(currencyDtoList.get(0));
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(
                DateFormaterUtil.getPastDate(1) + ".json",
                APP_ID, BASE_CURRENCY)).thenReturn(currencyDtoList.get(1));
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(
                DateFormaterUtil.getPastDate(0) + ".json",
                APP_ID, CURRENCY_CODE)).thenReturn(currencyDtoList.get(2));
        Mockito.when(openExchangeRatesClientMock.getUSDRateByDate(
                DateFormaterUtil.getPastDate(1) + ".json",
                APP_ID, CURRENCY_CODE)).thenReturn(currencyDtoList.get(3));
    }
}
