# Project Codebase Export

## File: `./.gitignore`

```text
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/
.kotlin

### IntelliJ IDEA ###
../.idea/modules.xml
.idea/jarRepositories.xml
.idea/compiler.xml
.idea/libraries/
*.iws
*.iml
*.ipr

### Eclipse ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

### Mac OS ###
.DS_Store
```

## File: `./README.md`

```text
# ssheavyhaulage
Website for SS HEAVY HAULAGE

# SS Heavy Haulage - End-User Logistics Portal

Enterprise heavy load transport application designed for South African logistics networks.

## Tech Stack
- **Backend:** Java 21, Spring Boot 3, Maven, JUnit 5
- **Frontend:** React, TypeScript, Tailwind CSS, Axios
- **IDE:** IntelliJ IDEA

## Getting Started

### Backend Setup
1. Open the `/backend` folder in IntelliJ IDEA.
2. Run `mvn clean install` to resolve dependencies.
3. Launch `SsHeavyHaulageApplication.java`.
4. API will run at `http://localhost:8080/api/v1`.

### Frontend Setup
1. Navigate to `/frontend` directory:
   ```bash
   cd frontend
   npm install
   npm start



### Folder & Directory Structure
ss-heavy-haulage/
├── backend/                        # Spring Boot Application (IntelliJ Module)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ssheavyhaulage/
│   │   │   │   ├── config/         # Security & CORS configuration
│   │   │   │   ├── controller/     # REST Endpoints (Quote, Fleet, Service controllers)
│   │   │   │   ├── dto/            # Request/Response Data Transfer Objects
│   │   │   │   ├── model/          # JPA Entities (FleetItem, QuoteRequest, Location)
│   │   │   │   ├── repository/     # Spring Data Repositories
│   │   │   │   └── service/        # Core Domain Logic (Distance & Fare Estimator)
│   │   │   └── resources/
│   │   │       └── application.yml # Environment variables & DB config
│   │   └── test/                   # JUnit 5 & Mockito Unit Tests
│   └── pom.xml                     # Maven Dependencies
│
└── frontend/                       # React + TypeScript SPA (IntelliJ Module)
├── public/
├── src/
│   ├── components/             # Reusable UI (Navbar, FleetCard, QuoteForm)
│   ├── pages/                  # LandingPage, ServicesPage, EstimatePage
│   ├── services/               # Axios API client modules
│   ├── types/                  # TypeScript interface definitions
│   └── App.tsx
├── package.json
└── tailwind.config.js



### Project Features
Fleet Showcase: Dynamic catalog of heavy-haul rigs and specialized low-beds.

Tri-Leg Distance Estimator: Computes travel distances (Base → Pickup → Destination → Base).

Instant Cost Calculation: Transparent ZAR rate estimation based on load weight and distance.
```

## File: `./Architectural_approach.md`

```text
Architectural Approach
For SS Heavy Haulage, we need a responsive end-user web application backed by precise distance calculations and dynamic pricing logic.

System Architecture & Stack Selection
Since IntelliJ IDEA is your primary IDE, we will build a clean, decoupled full-stack architecture:
1. Backend Layer (Java / Spring Boot 3 + Maven):
    Why: Spring Boot provides enterprise-grade scalability, robust REST API creation, seamless dependency injection, and native integration within IntelliJ IDEA.
    Data Layer: PostgreSQL (or H2 in-memory for fast local setup) with Spring Data JPA.
2. Frontend Layer (React + TypeScript + Tailwind CSS):
    Why: React gives us a single-page application (SPA) model for responsive interactivity. TypeScript guarantees type safety, while Tailwind CSS lets us create a high-contrast industrial design (e.g., dark charcoal base paired with vibrant orange accents) suitable for heavy haulage branding.
3. Routing & Mapping API (Google Maps Platform or OpenStreetMap/OSRM):
    Why: Heavy haulage routes require leg-by-leg routing to compute the full transit cost accurately:$$\text{Total Distance} = D_{\text{Base} \to \text{Pickup}} + D_{\text{Pickup} \to \text{Destination}} + D_{\text{Destination} \to \text{Base}}$$
    Using backend distance calculation via spatial APIs prevents client-side tampering and keeps pricing formulas secure.



Project Timeline & Delivery Estimates
Phase 1: Architecture & Setup (Days 1–3)
├── Setup IntelliJ Spring Boot Maven project & React frontend.
└── Configure project structure, dependencies, and environment configurations.

Phase 2: Core End-User Features (Days 4–9)
├── Develop interactive landing page with company branding & fleet showcase.
├── Build backend trip calculator logic (Base -> Pickup -> Destination -> Base).
└── Build frontend distance estimator form & integrate with backend API.

Phase 3: Integration & Polish (Days 10–14)
├── Add Google Maps Place Autocomplete integration.
├── Write automated backend unit tests and frontend validation.
└── Complete responsive UI styling and UI testing.
```

## File: `./backend/pom.xml`

```text
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
        <relativePath/>
    </parent>

    <groupId>com.ssheavyhaulage</groupId>
    <artifactId>ss-heavy-haulage-backend</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>ss-heavy-haulage-backend</name>
    <description>Backend logistics and quote calculation engine for SS Heavy Haulage</description>

    <properties>
        <java.version>25</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Spring Boot Web Starter for REST Controllers -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Validation Starter for DTO Request Validation (@Valid, @NotNull, etc.) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Spring Data JPA for Database Persistence -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- PostgreSQL Driver for Production Database Connection -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- H2 In-Memory Database for Fast Local Testing & Development -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok to Reduce Boilerplate Code (Getters, Setters, Builders) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot Test Starter (JUnit 5, Mockito, Spring Test) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>${java.version}</release>
                </configuration>
            </plugin>

            <!-- Spring Boot Maven Plugin for Executable Jar packaging -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## File: `./backend/src/test/java/com/ssheavyhaulage/controller/QuoteControllerTest.java`

```text
package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.service.QuoteCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteCalculatorService quoteCalculatorService;

    @Test
    @DisplayName("Should return 400 Bad Request with field errors when payload is invalid")
    void shouldRejectInvalidQuoteRequest() throws Exception {
        String invalidJson = """
                {
                    "pickupAddress": "",
                    "destinationAddress": "Durban Harbour",
                    "cargoWeightTons": -5.0
                }
                """;

        mockMvc.perform(post("/api/v1/quotes/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.pickupAddress").value("Pickup location is required"))
                .andExpect(jsonPath("$.cargoWeightTons").value("Cargo weight must be greater than zero"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when address cannot be geocoded")
    void shouldHandleUnresolvedLocation() throws Exception {
        when(quoteCalculatorService.calculateQuote(any()))
                .thenThrow(new IllegalArgumentException("Could not resolve South African location: Atlantis"));

        String requestJson = """
                {
                    "pickupAddress": "Atlantis",
                    "destinationAddress": "Durban Harbour",
                    "cargoWeightTons": 15.0
                }
                """;

        mockMvc.perform(post("/api/v1/quotes/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Could not resolve South African location: Atlantis"));
    }
}

```

## File: `./backend/src/test/java/com/ssheavyhaulage/controller/FleetControllerTest.java`

```text
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
```

## File: `./backend/src/test/java/com/ssheavyhaulage/service/QuoteCalculatorServiceTest.java`

```text
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;
import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteCalculatorServiceTest {

    @Mock
    private RoutingService routingService;

    private QuoteCalculatorService quoteCalculatorService;

    private final Coordinates kemptonDepot = new Coordinates(-26.1076, 28.2293);

    @BeforeEach
    void setUp() {
        quoteCalculatorService = new QuoteCalculatorService(
                routingService,
                kemptonDepot.lat(),
                kemptonDepot.lng(),
                1500.00,
                45.00
        );
    }

    @Test
    @DisplayName("Should calculate accurate tri-leg distance and ZAR cost with weight surcharge")
    void testCalculateQuoteSuccess() {
        Coordinates pickup = new Coordinates(-26.1384, 28.2571);
        Coordinates destination = new Coordinates(-29.8587, 31.0218);

        when(routingService.geocodeAddress("Kempton Park")).thenReturn(pickup);
        when(routingService.geocodeAddress("Durban Harbour")).thenReturn(destination);

        when(routingService.getDrivingDistanceKm(kemptonDepot, pickup)).thenReturn(10.0);
        when(routingService.getDrivingDistanceKm(pickup, destination)).thenReturn(570.0);
        when(routingService.getDrivingDistanceKm(destination, kemptonDepot)).thenReturn(580.0);

        QuoteRequest request = new QuoteRequest();
        request.setPickupAddress("Kempton Park");
        request.setDestinationAddress("Durban Harbour");
        request.setCargoWeightTons(20.0); // 10 excess tons -> 1.50 weight multiplier

        QuoteResponse response = quoteCalculatorService.calculateQuote(request);

        // Total distance: 10 + 570 + 580 = 1160 km
        // Cost: 1500 + (1160 * 45 * 1.50) = 1500 + 78300 = 79800.00 ZAR
        assertEquals(10.0, response.getBaseToPickupKm());
        assertEquals(570.0, response.getPickupToDestinationKm());
        assertEquals(580.0, response.getDestinationToBaseKm());
        assertEquals(1160.0, response.getTotalDistanceKm());
        assertEquals(79800.00, response.getEstimatedCostZAR());
        assertEquals("ZAR", response.getCurrency());
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/SsHeavyHaulageApplication.java`

```text
package com.ssheavyhaulage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsHeavyHaulageApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsHeavyHaulageApplication.class, args);
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/repository/FleetVehicleRepository.java`

```text
package com.ssheavyhaulage.repository;

import com.ssheavyhaulage.model.FleetVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleetVehicleRepository extends JpaRepository<FleetVehicle, Long> {
    List<FleetVehicle> findByAvailableTrue();
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/config/GlobalExceptionHandler.java`

```text
package com.ssheavyhaulage.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles invalid or unresolved South African locations (HTTP 400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleBadLocation(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    // Handles external geocoding/routing service outages (HTTP 503)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleServiceUnavailable(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", ex.getMessage()));
    }

    // Handles Jakarta Bean Validation failures on DTOs (@NotBlank, @Positive, etc.) (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/config/DataSeeder.java`

```text
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
```

## File: `./backend/src/main/java/com/ssheavyhaulage/config/AppConfig.java`

```text
package com.ssheavyhaulage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                // Nominatim requires a valid custom User-Agent header per usage policy
                .defaultHeader("User-Agent", "SSHeavyHaulagePortal/1.0 (contact@ssheavyhaulage.co.za)")
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/controller/QuoteController.java`

```text
// backend/src/main/java/com/ssheavyhaulage/controller/QuoteController.java
package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import com.ssheavyhaulage.service.QuoteCalculatorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quotes")
@CrossOrigin(origins = "http://localhost:3000") // Secure CORS for React frontend
public class QuoteController {

    private final QuoteCalculatorService quoteCalculatorService;

    public QuoteController(QuoteCalculatorService quoteCalculatorService) {
        this.quoteCalculatorService = quoteCalculatorService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<QuoteResponse> calculateEstimate(@Valid @RequestBody QuoteRequest request) {
        QuoteResponse response = quoteCalculatorService.calculateQuote(request);
        return ResponseEntity.ok(response);
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/controller/FleetController.java`

```text
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
```

## File: `./backend/src/main/java/com/ssheavyhaulage/service/OsrmRoutingService.java`

```text
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

```

## File: `./backend/src/main/java/com/ssheavyhaulage/service/QuoteCalculatorService.java`

```text
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;
import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuoteCalculatorService {

    private final RoutingService routingService;
    private final Coordinates baseDepot;
    private final double baseFlatFee;
    private final double ratePerKm;

    public QuoteCalculatorService(
            RoutingService routingService,
            @Value("${haulage.base.lat}") double baseLat,
            @Value("${haulage.base.lng}") double baseLng,
            @Value("${haulage.base.flat-fee-zar}") double baseFlatFee,
            @Value("${haulage.base.rate-per-km-zar}") double ratePerKm) {
        this.routingService = routingService;
        this.baseDepot = new Coordinates(baseLat, baseLng);
        this.baseFlatFee = baseFlatFee;
        this.ratePerKm = ratePerKm;
    }

    public QuoteResponse calculateQuote(QuoteRequest request) {
        Coordinates pickupCoords = routingService.geocodeAddress(request.getPickupAddress());
        Coordinates destinationCoords = routingService.geocodeAddress(request.getDestinationAddress());

        double leg1 = routingService.getDrivingDistanceKm(baseDepot, pickupCoords);
        double leg2 = routingService.getDrivingDistanceKm(pickupCoords, destinationCoords);
        double leg3 = routingService.getDrivingDistanceKm(destinationCoords, baseDepot);

        double totalDistanceKm = roundTwoDecimals(leg1 + leg2 + leg3);

        // Weight surcharge: 5% increase per ton above standard 10-ton base load
        double excessTons = Math.max(0.0, request.getCargoWeightTons() - 10.0);
        double weightFactor = 1.0 + (excessTons * 0.05);

        double totalCost = baseFlatFee + (totalDistanceKm * ratePerKm * weightFactor);

        return QuoteResponse.builder()
                .baseToPickupKm(roundTwoDecimals(leg1))
                .pickupToDestinationKm(roundTwoDecimals(leg2))
                .destinationToBaseKm(roundTwoDecimals(leg3))
                .totalDistanceKm(totalDistanceKm)
                .estimatedCostZAR(roundTwoDecimals(totalCost))
                .currency("ZAR")
                .build();
    }

    private double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/service/RoutingService.java`

```text
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.Coordinates;

public interface RoutingService {
    Coordinates geocodeAddress(String address);
    double getDrivingDistanceKm(Coordinates origin, Coordinates destination);
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java`

```text
// backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java
package com.ssheavyhaulage.dto;

public record QuoteResponse(
        double baseToPickupKm,
        double pickupToDestinationKm,
        double destinationToBaseKm,
        double totalDistanceKm,
        double estimatedCostZAR,
        String currency
) {}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/dto/QuoteRequest.java`

```text
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
```

## File: `./backend/src/main/java/com/ssheavyhaulage/dto/Coordinates.java`

```text
package com.ssheavyhaulage.dto;

public record Coordinates(double lat, double lng) {}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/model/FleetVehicle.java`

```text
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
```

## File: `./backend/src/main/resources/application.yml`

```text
server:
  port: 8080

spring:
  application:
    name: ss-heavy-haulage-backend
  datasource:
    url: jdbc:h2:mem:sshaulagedb;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password: ""
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console

# Custom Haulage Pricing & Base Hub Config (Gauteng / Kempton Park Depot)
haulage:
  base:
    lat: -26.1076
    lng: 28.2293
    flat-fee-zar: 1500.00
    rate-per-km-zar: 45.00
```

## File: `./frontend/tailwind.config.js`

```text

```

## File: `./frontend/package.json`

```text

```

## File: `./frontend/src/App.tsx`

```text

```

