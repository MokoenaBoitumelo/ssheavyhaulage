package com.ssheavyhaulage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "fleet_vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleetVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name; // e.g., "Freightliner Argosy Cab-Over"

    @NotBlank
    @Column(nullable = false)
    private String trailerType; // e.g., "34-Ton Superlink Tautliner", "50-Ton Lowbed"

    @Positive
    @Column(nullable = false)
    private Double maxPayloadTons;

    @Column(length = 500)
    private String description;

    private String imageUrl;

    @Builder.Default
    private boolean available = true;
}