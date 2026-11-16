package com.ssheavyhaulage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssheavyhaulage.dto.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class OsrmRoutingService implements RoutingService{

    private static final Logger log = LoggerFactory.getLogger(OsrmRoutingService.class);
    private final RestClient restClient;

    public OsrmRoutingService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Coordinates geocodeAddress(String address) {
        String encodedQuery = UriUtils.encode(address, StandardCharsets.UTF_8);
        String url = "https://nominatim.openstreetmap.org/search?q=" + encodedQuery
                + "&countrycodes=za&format=json&limit=1";

        try {
            JsonNode response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(JsonNode.class);

            if (response != null && response.isArray() && !response.isEmpty()) {
                JsonNode firstMatch = response.get(0);
                double lat = firstMatch.get("lat").asDouble();
                double lon = firstMatch.get("lon").asDouble();
                return new Coordinates(lat, lon);
            }
            throw new IllegalArgumentException("Could not resolve South African location: " + address);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Geocoding API unreachable for '{}', falling back to default depot offset: {}", address, e.getMessage());
            throw new IllegalStateException("Location lookup service temporarily unavailable. Please try again.");
        }
    }

    @Override
    public double getDrivingDistanceKm(Coordinates origin, Coordinates destination) {
        // OSRM expects coordinates in longitude,latitude order
        String url = String.format(
                "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=false",
                origin.lng(), origin.lat(), destination.lng(), destination.lat()
        );

        try {
            JsonNode response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(JsonNode.class);

            if (response != null && "Ok".equals(response.path("code").asText())) {
                double distanceMeters = response.path("routes").get(0).path("distance").asDouble();
                return distanceMeters / 1000.0;
            }
        } catch (Exception e) {
            log.warn("OSRM routing failed, falling back to Haversine road approximation: {}", e.getMessage());
        }

        return calculateHaversineFallback(origin, destination);
    }

    private double calculateHaversineFallback(Coordinates c1, Coordinates c2) {
        final int earthRadiusKm = 6371;
        double dLat = Math.toRadians(c2.lat() - c1.lat());
        double dLon = Math.toRadians(c2.lng() - c1.lng());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(c1.lat())) * Math.cos(Math.toRadians(c2.lat()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c * 1.25; // 1.25 road curvature factor
    }
}
