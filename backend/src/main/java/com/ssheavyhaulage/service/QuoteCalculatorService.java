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

    public QuoteResponse calculateQuote(QuoteRequest request) {
        Coordinates pickupCoords = routingService.geocodeAddress(request.pickupAddress());
        Coordinates destinationCoords = routingService.geocodeAddress(request.destinationAddress());

        double leg1 = routingService.getDrivingDistanceKm(baseDepot, pickupCoords);
        double leg2 = routingService.getDrivingDistanceKm(pickupCoords, destinationCoords);
        double leg3 = routingService.getDrivingDistanceKm(destinationCoords, baseDepot);

        double totalDistanceKm = roundTwoDecimals(leg1 + leg2 + leg3);

        // Weight surcharge: 5% increase per ton above standard 10-ton base load
        double excessTons = Math.max(0.0, request.cargoWeightTons() - 10.0);
        double weightFactor = 1.0 + (excessTons * 0.05);

        double totalCost = baseFlatFee + (totalDistanceKm * ratePerKm * weightFactor);

        return new QuoteResponse(
                roundTwoDecimals(leg1),
                roundTwoDecimals(leg2),
                roundTwoDecimals(leg3),
                totalDistanceKm,
                roundTwoDecimals(totalCost),
                "ZAR"
        );
    }

    private double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}