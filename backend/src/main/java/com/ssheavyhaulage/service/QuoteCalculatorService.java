package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;
import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuoteCalculatorService {

    private final RoutingService routingService;
    private final Coordinates baseDepot;
    private final double baseFlatFee;
    private final double ratePerKm;

    public QuoteCalculatorService(
            RoutingService routingService,
            @Value("${haulage.base.lat}") double baseLat,
            @Value("${haulage.base.lng}") double baseLng,
            @Value("${haulage.base.flat-fee-zar}") double baseFlatFee,
            @Value("${haulage.base.rate-per-km-zar}") double ratePerKm) {
        this.routingService = routingService;
        this.baseDepot = new Coordinates(baseLat, baseLng);
        this.baseFlatFee = baseFlatFee;
        this.ratePerKm = ratePerKm;
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