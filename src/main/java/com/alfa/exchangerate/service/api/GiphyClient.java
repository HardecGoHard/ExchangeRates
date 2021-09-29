package com.alfa.exchangerate.service.api;

import com.alfa.exchangerate.dto.GiphyDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "api-Giphy", url = "${giphy.api.url}")
public interface GiphyClient {
    @GetMapping("${giphy.api.random}")
    GiphyDataDto getRandomGif(@RequestParam("api_key") String apiKey, @RequestParam("tag") String tag,
                              @RequestParam("rating") String rating);
}
