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

        QuoteRequest request = new QuoteRequest();
        request.setPickupAddress("Kempton Park");
        request.setDestinationAddress("Durban Harbour");
        request.setCargoWeightTons(20.0); // 10 excess tons -> 1.50 weight multiplier

        QuoteResponse response = quoteCalculatorService.calculateQuote(request);

        // Total distance: 10 + 570 + 580 = 1160 km
        // Cost: 1500 + (1160 * 45 * 1.50) = 1500 + 78300 = 79800.00 ZAR
        assertEquals(10.0, response.getBaseToPickupKm());
        assertEquals(570.0, response.getPickupToDestinationKm());
        assertEquals(580.0, response.getDestinationToBaseKm());
        assertEquals(1160.0, response.getTotalDistanceKm());
        assertEquals(79800.00, response.getEstimatedCostZAR());
        assertEquals("ZAR", response.getCurrency());
    }
}