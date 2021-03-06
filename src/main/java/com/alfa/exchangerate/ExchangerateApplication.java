package com.alfa.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangerateApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExchangerateApplication.class, args);
    }
}
