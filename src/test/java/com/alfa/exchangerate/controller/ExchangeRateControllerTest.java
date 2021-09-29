package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.dto.GifDto;
import com.alfa.exchangerate.dto.GiphyDataDto;
import com.alfa.exchangerate.service.ExchangeRatesService;
import com.alfa.exchangerate.service.GifService;
import com.alfa.exchangerate.service.api.GiphyClient;
import com.alfa.exchangerate.service.api.OpenExchangeRatesClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeRateController.class)
@AutoConfigureMockMvc
public class ExchangeRateControllerTest {
    @MockBean
    private ExchangeRatesService exchangeRatesServiceMock;
    @MockBean
    private GifService gifServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getExchangeGif_Rich() throws Exception {
        String richUrl = "random rich gif url";
        Mockito.when(exchangeRatesServiceMock.isExchangeRateIsInc("USD")).thenReturn(true);
        Mockito.when(gifServiceMock.getRandomGifByTag("rich")).thenReturn(
                new GiphyDataDto(new GifDto("richGif", richUrl)));
        this.mockMvc.perform(get("/exchange/USD"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"))
                .andExpect(model().attribute("text", "You are rich!"))
                .andExpect(model().attribute("gifurl", richUrl));

    }
    @Test
    public void getExchangeGif_Broke() throws Exception {
        String brokeUrl = "random broke gif url";
        Mockito.when(exchangeRatesServiceMock.isExchangeRateIsInc("USD")).thenReturn(false);
        Mockito.when(gifServiceMock.getRandomGifByTag("broke")).thenReturn(
                new GiphyDataDto(new GifDto("brokeGif", brokeUrl)));
        this.mockMvc.perform(get("/exchange/USD"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"))
                .andExpect(model().attribute("text", "You are broke!"))
                .andExpect(model().attribute("gifurl", brokeUrl));

    }
}
