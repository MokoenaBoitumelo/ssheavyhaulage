// backend/src/test/java/com/ssheavyhaulage/service/QuoteCalculatorServiceTest.java
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteCalculatorServiceTest {

    private QuoteCalculatorService quoteCalculatorService;

    @BeforeEach
    void setUp() {
        quoteCalculatorService = new QuoteCalculatorService();
    }

    @Test
    @DisplayName("Should correctly calculate tri-leg total distance and estimate fare in ZAR")
    void testCalculateQuoteSuccess() {
        QuoteRequest request = new QuoteRequest();
        request.setPickupAddress("O.R. Tambo International Airport, Kempton Park");
        request.setDestinationAddress("Durban Harbour, KwaZulu-Natal");
        request.setCargoWeightTons(15.0);

        QuoteResponse response = quoteCalculatorService.calculateQuote(request);

        assertNotNull(response);
        assertTrue(response.getTotalDistanceKm() > 0);
        assertTrue(response.getEstimatedCostZAR() > 0);
        assertEquals("ZAR", response.getCurrency());
    }
}