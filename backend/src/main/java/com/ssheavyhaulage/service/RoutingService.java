package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;

public interface RoutingService {
    Coordinates geocodeAddress(String address);
    double getDrivingDistanceKm(Coordinates origin, Coordinates destination);
}