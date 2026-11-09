// backend/src/main/java/com/ssheavyhaulage/service/QuoteCalculatorService.java
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.springframework.stereotype.Service;

@Service
public class QuoteCalculatorService {

    // Base location: e.g., Johannesburg Fleet Hub
    private static final double BASE_LAT = -26.2041;
    private static final double BASE_LNG = 28.0473;

    private static final double BASE_FLAT_FEE = 1500.00; // Standard base dispatch fee in ZAR
    private static final double RATE_PER_KM = 45.00;     // Base rate per kilometer in ZAR

    public QuoteResponse calculateQuote(QuoteRequest request) {
        // Mock distance extraction (In production, replace with Google Matrix / OSRM API call)
        double leg1 = simulateDistance(BASE_LAT, BASE_LNG, -26.1384, 28.2571); // Base -> Pickup
        double leg2 = simulateDistance(-26.1384, 28.2571, -29.8587, 31.0218); // Pickup -> Destination
        double leg3 = simulateDistance(-29.8587, 31.0218, BASE_LAT, BASE_LNG); // Destination -> Base

        double totalDistanceKm = Math.round((leg1 + leg2 + leg3) * 100.0) / 100.0;

        // Weight surcharge calculation: 5% surcharge for every 10 tons
        double weightFactor = 1.0 + (Math.max(0, request.getCargoWeightTons() - 10) * 0.05);
        double totalCost = BASE_FLAT_FEE + (totalDistanceKm * RATE_PER_KM * weightFactor);

        return QuoteResponse.builder()
                .baseToPickupKm(Math.round(leg1 * 100.0) / 100.0)
                .pickupToDestinationKm(Math.round(leg2 * 100.0) / 100.0)
                .destinationToBaseKm(Math.round(leg3 * 100.0) / 100.0)
                .totalDistanceKm(totalDistanceKm)
                .estimatedCostZAR(Math.round(totalCost * 100.0) / 100.0)
                .currency("ZAR")
                .build();
    }

    private double simulateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula calculation for distance in KM
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1.3; // 1.3 road winding factor approximation
    }
}