// backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java
package com.ssheavyhaulage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteResponse {
    private double baseToPickupKm;
    private double pickupToDestinationKm;
    private double destinationToBaseKm;
    private double totalDistanceKm;
    private double estimatedCostZAR;
    private String currency; // ZAR (R)
}