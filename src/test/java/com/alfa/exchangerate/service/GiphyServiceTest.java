package com.alfa.exchangerate.service;

import com.alfa.exchangerate.dto.GifDto;
import com.alfa.exchangerate.dto.GiphyDataDto;
import com.alfa.exchangerate.service.api.GiphyClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class GiphyServiceTest {
    private static final String APP_KEY = "iKiELaiFe6ryHaJKSYVPA5JYnvTvSUtb";
    private static final String RATING = "g";

    private static GiphyClient giphyClientMock;
    private static GifService gifService;
    private static GiphyDataDto giphyDataDto;


    @BeforeAll
    public static void setUp() {
        giphyDataDto = new GiphyDataDto(
                new GifDto("Gif", "http://api.giphy.com")
        );

        giphyClientMock = Mockito.mock(GiphyClient.class);
        gifService = new GifService(giphyClientMock,APP_KEY,RATING);
    }

    @Test
    public void getRandomGif(){
            Mockito.when(giphyClientMock.getRandomGif(APP_KEY, "rich", RATING)).thenReturn(giphyDataDto);
            gifService.getRandomGifByTag("rich");
            assertThat(gifService.getRandomGifByTag("rich")).isEqualTo(giphyDataDto);
    }
}
