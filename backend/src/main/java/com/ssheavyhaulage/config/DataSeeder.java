package com.ssheavyhaulage.config;

import com.ssheavyhaulage.model.FleetVehicle;
import com.ssheavyhaulage.repository.FleetVehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initFleetData(FleetVehicleRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        FleetVehicle.builder()
                                .name("Freightliner Cab-Over Heavy Rig")
                                .trailerType("34-Ton Superlink Flatdeck")
                                .maxPayloadTons(34.0)
                                .description("Long-haul cross-border and national corridor heavy freight transport.")
                                .imageUrl("/assets/fleet-freightliner.png")
                                .available(true)
                                .build(),
                        FleetVehicle.builder()
                                .name("Tri-Axle Abnormal Lowbed")
                                .trailerType("55-Ton Hydraulic Detachable Gooseneck")
                                .maxPayloadTons(55.0)
                                .description("Specialized heavy plant, mining machinery, and yellow-metal transport.")
                                .imageUrl("/assets/fleet-lowbed.png")
                                .available(true)
                                .build(),
                        FleetVehicle.builder()
                                .name("Tautliner Curtain-Sider Link")
                                .trailerType("32-Ton Side-Loading Interlink")
                                .maxPayloadTons(32.0)
                                .description("Weather-protected palletized commercial and industrial goods.")
                                .imageUrl("/assets/fleet-tautliner.png")
                                .available(true)
                                .build()
                ));
            }
        };
    }
}