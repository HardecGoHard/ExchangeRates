package com.alfa.exchangerate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ExceptionHandlerController.class)
public class ExceptionHandlerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String INCORRECT_CODE_ID = "HHH";

    @Test
    void notFoundException_Should_True() throws Exception {
        mockMvc.perform(get("/exchange/" + INCORRECT_CODE_ID))
                .andExpect(status().is4xxClientError());
    }
}
