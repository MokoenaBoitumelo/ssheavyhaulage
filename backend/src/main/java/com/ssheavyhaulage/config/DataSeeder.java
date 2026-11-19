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
                        new FleetVehicle(
                                null,
                                "Freightliner Cab-Over Heavy Rig",
                                "34-Ton Superlink Flatdeck",
                                34.0,
                                "Long-haul cross-border and national corridor heavy freight transport.",
                                "/assets/fleet-freightliner.png",
                                true
                        ),
                        new FleetVehicle(
                                null,
                                "Tri-Axle Abnormal Lowbed",
                                "55-Ton Hydraulic Detachable Gooseneck",
                                55.0,
                                "Specialized heavy plant, mining machinery, and yellow-metal transport.",
                                "/assets/fleet-lowbed.png",
                                true
                        ),
                        new FleetVehicle(
                                null,
                                "Tautliner Curtain-Sider Link",
                                "32-Ton Side-Loading Interlink",
                                32.0,
                                "Weather-protected palletized commercial and industrial goods.",
                                "/assets/fleet-tautliner.png",
                                true
                        )
                ));
            }
        };
    }
}