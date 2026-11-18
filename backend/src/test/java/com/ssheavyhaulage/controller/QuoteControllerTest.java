package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.service.QuoteCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteCalculatorService quoteCalculatorService;

    @Test
    @DisplayName("Should return 400 Bad Request with field errors when payload is invalid")
    void shouldRejectInvalidQuoteRequest() throws Exception {
        String invalidJson = """
                {
                    "pickupAddress": "",
                    "destinationAddress": "Durban Harbour",
                    "cargoWeightTons": -5.0
                }
                """;

        mockMvc.perform(post("/api/v1/quotes/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.pickupAddress").value("Pickup location is required"))
                .andExpect(jsonPath("$.cargoWeightTons").value("Cargo weight must be greater than zero"));
    }
}
