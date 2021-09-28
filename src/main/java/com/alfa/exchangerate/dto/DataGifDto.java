package com.alfa.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGifDto {
    @JsonProperty("type")
    String type;
    @JsonProperty("image_original_url")
    String url;
}
