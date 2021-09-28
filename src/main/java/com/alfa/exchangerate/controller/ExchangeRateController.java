package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.service.ExchangeRatesService;
import com.alfa.exchangerate.service.GiphyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/exchange")
public class ExchangeRateController {
    private final ExchangeRatesService exchangeRatesService;
    private final GiphyService giphyService;

    public ExchangeRateController(ExchangeRatesService apiOpenExchangeRates, GiphyService giphyService) {
        this.exchangeRatesService = apiOpenExchangeRates;
        this.giphyService = giphyService;
    }

    @GetMapping("/{currencyId}")
    public String getExchangeGif(@PathVariable("currencyId") String currencyId, Model model) {
        currencyId = currencyId.toUpperCase();
        if (exchangeRatesService.isExchangeRateIsInc(currencyId)) {
            model.addAttribute("text", "You are rich!");
            model.addAttribute("gifurl", giphyService.getRandomGifByTag("Rich").getDataGifDto().getUrl());
            return "main_page";
        }
        model.addAttribute("text", "You are broken!");
        model.addAttribute("gifurl", giphyService.getRandomGifByTag("Broke").getDataGifDto().getUrl());
        return "main_page";
    }
}
