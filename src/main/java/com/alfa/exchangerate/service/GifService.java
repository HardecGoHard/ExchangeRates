package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.GiphyDataDto;
import com.alfa.exchangerate.service.api.GiphyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GifService {
    protected static final Logger LOGGER = Logger.getLogger(GifService.class.getName());

    private final GiphyClient giphyClient;

    private String apiKey;
    private String searchRating;

    @Autowired
    public GifService(GiphyClient giphyClient,
                      @Value("${giphy.api.app_id}") String apiKey,
                      @Value("${giphy.api.rating}") String searchRating) {
        this.giphyClient = giphyClient;
        this.apiKey = apiKey;
        this.searchRating = searchRating;
    }

    public GiphyDataDto getRandomGifByTag(String tag) {
        LOGGER.info("getting random gif from Giphy.com");
        return giphyClient.getRandomGif(apiKey, tag, searchRating);
    }
}
