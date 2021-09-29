package com.alfa.exchangerate.util;

import lombok.NonNull;

public class ExchangeCalculateUtil {

    //to find out the exchange rate to the ruble, I had to write a small util calculator

    public static Double getRUBRate(@NonNull Double baseCurrency_RubRate,
                                    @NonNull Double baseCurrency_currencyCodeRate) {
        return baseCurrency_RubRate / baseCurrency_currencyCodeRate;
    }
}
