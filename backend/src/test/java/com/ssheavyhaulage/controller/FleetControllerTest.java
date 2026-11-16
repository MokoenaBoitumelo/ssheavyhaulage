package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.model.FleetVehicle;
import com.ssheavyhaulage.repository.FleetVehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FleetController.class)
class FleetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FleetVehicleRepository fleetRepository;

    @Test
    void shouldReturnAvailableFleetVehicles() throws Exception {
        FleetVehicle rig = FleetVehicle.builder()
                .id(1L)
                .name("Freightliner Cab-Over")
                .trailerType("34-Ton Superlink")
                .maxPayloadTons(34.0)
                .available(true)
                .build();

        when(fleetRepository.findByAvailableTrue()).thenReturn(List.of(rig));

        mockMvc.perform(get("/api/v1/fleet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Freightliner Cab-Over"))
                .andExpect(jsonPath("$[0].maxPayloadTons").value(34.0));
    }
}