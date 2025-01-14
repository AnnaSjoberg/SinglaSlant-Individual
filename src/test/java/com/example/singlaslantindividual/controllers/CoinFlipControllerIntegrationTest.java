package com.example.singlaslantindividual.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("IntegrationTest")
public class CoinFlipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publishFlipStatus200() throws Exception {
        this.mockMvc.perform(post("/flip?choice=" + "heads"))
                .andExpect(status().isOk());
    }

    @Test
    void publishFlipStatus200ChoiceInvalid() throws Exception {
        this.mockMvc.perform(post("/flip?choice=" + "tummy"))
                .andExpect(status().isOk());
    }

    @Test
    void publishFlipReturnsViewIndex() throws Exception {
        this.mockMvc.perform(post("/flip?choice=" + "tails"))
                .andExpect(view().name("index"));
    }

    @Test
    public void homePageReturnsViewIndex() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    public void homePageContainsString_CoinFlipGame() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(content().string(containsString("Coin Flip Game")));
    }
}
