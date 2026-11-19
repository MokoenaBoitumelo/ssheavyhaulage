// backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java
package com.ssheavyhaulage.dto;

public record QuoteResponse(
        double baseToPickupKm,
        double pickupToDestinationKm,
        double destinationToBaseKm,
        double totalDistanceKm,
        double estimatedCostZAR,
        String currency
) {}