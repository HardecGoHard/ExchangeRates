package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.service.ExchangeRatesService;
import com.alfa.exchangerate.service.GifService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange")
public class ExchangeRateController {
    private final ExchangeRatesService exchangeRatesService;
    private final GifService gifService;

    public ExchangeRateController(ExchangeRatesService apiOpenExchangeRates, GifService gifService) {
        this.exchangeRatesService = apiOpenExchangeRates;
        this.gifService = gifService;
    }

    @GetMapping("/{currencyId}")
    public String getExchangeGif(@PathVariable("currencyId") String currencyId, Model model) {
        currencyId = currencyId.toUpperCase();
        if (exchangeRatesService.isExchangeRateIsInc(currencyId)) {
            model.addAttribute("text", "You are rich!");
            model.addAttribute("gifurl", gifService.getRandomGifByTag("rich").getGifDto().getUrl());
            return "main_page";
        }
        model.addAttribute("text", "You are broke!");
        model.addAttribute("gifurl", gifService.getRandomGifByTag("broke").getGifDto().getUrl());
        return "main_page";
    }
}
