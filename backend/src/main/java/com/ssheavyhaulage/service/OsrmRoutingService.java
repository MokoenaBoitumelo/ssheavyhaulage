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
}
