package com.ssheavyhaulage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record QuoteRequest(
        @NotBlank(message = "Pickup location is required")
        String pickupAddress,

        @NotBlank(message = "Destination location is required")
        String destinationAddress,

        @NotNull(message = "Cargo weight is required")
        @Positive(message = "Cargo weight must be greater than zero")
        Double cargoWeightTons
) {}