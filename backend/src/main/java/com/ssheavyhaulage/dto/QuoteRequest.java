// backend/src/main/java/com/ssheavyhaulage/dto/QuoteRequest.java
package com.ssheavyhaulage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuoteRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickupAddress;

    @NotBlank(message = "Destination location is required")
    private String destinationAddress;

    @NotNull(message = "Cargo weight is required")
    private Double cargoWeightTons; // Tonnage affects per-km multiplier
}