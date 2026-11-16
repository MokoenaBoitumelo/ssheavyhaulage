package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.model.FleetVehicle;
import com.ssheavyhaulage.repository.FleetVehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fleet")
@CrossOrigin(origins = "http://localhost:3000")
public class FleetController {

    private final FleetVehicleRepository fleetRepository;

    public FleetController(FleetVehicleRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    @GetMapping
    public ResponseEntity<List<FleetVehicle>> getAvailableFleet() {
        return ResponseEntity.ok(fleetRepository.findByAvailableTrue());
    }
}