package com.alfa.exchangerate.util;

import lombok.NonNull;

public class ExchangeCalculateUtil {
    /* Базовый аккаунт OpenExchangeRates позволяет выбрать в качестве стандартной валюты USD,
        чтобы узнать курс к рублю пришлось написать маленький утильный калькулятор
     */
    public static Double getRUBRate(@NonNull Double baseCurrency_RubRate,
                                    @NonNull Double baseCurrency_currencyCodeRate) {
        return baseCurrency_RubRate / baseCurrency_currencyCodeRate;
    }
}
