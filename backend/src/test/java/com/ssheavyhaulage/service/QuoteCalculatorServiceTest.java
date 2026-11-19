package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;
import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteCalculatorServiceTest {

    @Mock
    private RoutingService routingService;

    private QuoteCalculatorService quoteCalculatorService;

    private final Coordinates kemptonDepot = new Coordinates(-26.1076, 28.2293);

    @BeforeEach
    void setUp() {
        quoteCalculatorService = new QuoteCalculatorService(
                routingService,
                kemptonDepot.lat(),
                kemptonDepot.lng(),
                1500.00,
                45.00
        );
    }

    @Test
    @DisplayName("Should calculate accurate tri-leg distance and ZAR cost with weight surcharge")
    void testCalculateQuoteSuccess() {
        Coordinates pickup = new Coordinates(-26.1384, 28.2571);
        Coordinates destination = new Coordinates(-29.8587, 31.0218);

        when(routingService.geocodeAddress("Kempton Park")).thenReturn(pickup);
        when(routingService.geocodeAddress("Durban Harbour")).thenReturn(destination);

        when(routingService.getDrivingDistanceKm(kemptonDepot, pickup)).thenReturn(10.0);
        when(routingService.getDrivingDistanceKm(pickup, destination)).thenReturn(570.0);
        when(routingService.getDrivingDistanceKm(destination, kemptonDepot)).thenReturn(580.0);

        QuoteRequest request = new QuoteRequest("Kempton Park", "Durban Harbour", 20.0);

        QuoteResponse response = quoteCalculatorService.calculateQuote(request);

        assertEquals(10.0, response.baseToPickupKm());
        assertEquals(570.0, response.pickupToDestinationKm());
        assertEquals(580.0, response.destinationToBaseKm());
        assertEquals(1160.0, response.totalDistanceKm());
        assertEquals(79800.00, response.estimatedCostZAR());
        assertEquals("ZAR", response.currency());
    }
}