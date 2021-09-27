package com.alfa.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("base")
    private String base;
    @JsonProperty("rates")
    private Map<String, Double> rates;
}
