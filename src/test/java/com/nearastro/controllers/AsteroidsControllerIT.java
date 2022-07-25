package com.nearastro.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AsteroidsControllerIT {

    @Autowired
    private MockMvc testClient;

    @Test
    @DisplayName("fetch 10 asteroids")
    public void fetch10() throws Exception {
        var start = LocalDate.of(2015, 9, 1);
        var end = LocalDate.of(2015, 9, 20);
        testClient.perform(
                        get("/asteroids")
                                .queryParam("start", start.toString())
                                .queryParam("end", end.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

    }

    @Test
    @DisplayName("Fetch largest for 2010")
    public void fetchLargest() throws Exception {
        testClient.perform(
                get("/asteroids/largest/2010")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("2003122")));
    }
}