package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.DataGifDto;
import com.alfa.exchangerate.dto.GiphyDataDto;
import com.alfa.exchangerate.exception.ExchangeRateException;
import com.alfa.exchangerate.service.api.GiphyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GiphyService {
    protected static final Logger LOGGER = Logger.getLogger(GiphyService.class.getName());

    private final GiphyClient giphyClient;

    @Value("${giphy.api.app_id}")
    private String apiKey;
    @Value("${giphy.api.rating}")
    private String searchRating;

    @Autowired
    public GiphyService(GiphyClient giphyClient) {
        this.giphyClient = giphyClient;
    }

    public GiphyDataDto getRandomGifByTag(String tag){
        LOGGER.info("getting random gif from Giphy.com");
        return giphyClient.getRandomGif(apiKey,tag,searchRating);
    }
}
