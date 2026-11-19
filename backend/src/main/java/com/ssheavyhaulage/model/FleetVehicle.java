// backend/src/main/java/com/ssheavyhaulage/model/FleetVehicle.java
package com.ssheavyhaulage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "fleet_vehicles")
public class FleetVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String trailerType;

    @Positive
    @Column(nullable = false)
    private Double maxPayloadTons;

    @Column(length = 500)
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private boolean available = true;

    public FleetVehicle() {}

    public FleetVehicle(Long id, String name, String trailerType, Double maxPayloadTons,
                        String description, String imageUrl, boolean available) {
        this.id = id;
        this.name = name;
        this.trailerType = trailerType;
        this.maxPayloadTons = maxPayloadTons;
        this.description = description;
        this.imageUrl = imageUrl;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTrailerType() { return trailerType; }
    public void setTrailerType(String trailerType) { this.trailerType = trailerType; }

    public Double getMaxPayloadTons() { return maxPayloadTons; }
    public void setMaxPayloadTons(Double maxPayloadTons) { this.maxPayloadTons = maxPayloadTons; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}