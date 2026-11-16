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

## File: `./pom.xml`

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
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.ssheavyhaulage</groupId>
    <artifactId>ss-heavy-haulage-backend</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>ss-heavy-haulage-backend</name>
    <description>Backend logistics and quote calculation engine for SS Heavy Haulage</description>

    <properties>
        <java.version>21</java.version>
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
            <!-- Spring Boot Maven Plugin for Executable Jar packaging -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </dependency>
        </plugins>
    </build>
</project>
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


    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.0</version>


    <groupId>com.ssheavyhaulage</groupId>
    <artifactId>ss-heavy-haulage-backend</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>ss-heavy-haulage-backend</name>
    <description>Backend logistics and quote calculation engine for SS Heavy Haulage</description>

    <properties>
        <java.version>21</java.version>
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
// backend/src/test/java/com/ssheavyhaulage/service/QuoteCalculatorServiceTest.java
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteCalculatorServiceTest {

    private QuoteCalculatorService quoteCalculatorService;

    @BeforeEach
    void setUp() {
        quoteCalculatorService = new QuoteCalculatorService();
    }

    @Test
    @DisplayName("Should correctly calculate tri-leg total distance and estimate fare in ZAR")
    void testCalculateQuoteSuccess() {
        QuoteRequest request = new QuoteRequest();
        request.setPickupAddress("O.R. Tambo International Airport, Kempton Park");
        request.setDestinationAddress("Durban Harbour, KwaZulu-Natal");
        request.setCargoWeightTons(15.0);

        QuoteResponse response = quoteCalculatorService.calculateQuote(request);

        assertNotNull(response);
        assertTrue(response.getTotalDistanceKm() > 0);
        assertTrue(response.getEstimatedCostZAR() > 0);
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

## File: `./backend/src/main/java/com/ssheavyhaulage/service/QuoteCalculatorService.java`

```text
// backend/src/main/java/com/ssheavyhaulage/service/QuoteCalculatorService.java
package com.ssheavyhaulage.service;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import org.springframework.stereotype.Service;

@Service
public class QuoteCalculatorService {

    // Base location: e.g., Johannesburg Fleet Hub
    private static final double BASE_LAT = -26.2041;
    private static final double BASE_LNG = 28.0473;

    private static final double BASE_FLAT_FEE = 1500.00; // Standard base dispatch fee in ZAR
    private static final double RATE_PER_KM = 45.00;     // Base rate per kilometer in ZAR

    public QuoteResponse calculateQuote(QuoteRequest request) {
        // Mock distance extraction (In production, replace with Google Matrix / OSRM API call)
        double leg1 = simulateDistance(BASE_LAT, BASE_LNG, -26.1384, 28.2571); // Base -> Pickup
        double leg2 = simulateDistance(-26.1384, 28.2571, -29.8587, 31.0218); // Pickup -> Destination
        double leg3 = simulateDistance(-29.8587, 31.0218, BASE_LAT, BASE_LNG); // Destination -> Base

        double totalDistanceKm = Math.round((leg1 + leg2 + leg3) * 100.0) / 100.0;

        // Weight surcharge calculation: 5% surcharge for every 10 tons
        double weightFactor = 1.0 + (Math.max(0, request.getCargoWeightTons() - 10) * 0.05);
        double totalCost = BASE_FLAT_FEE + (totalDistanceKm * RATE_PER_KM * weightFactor);

        return QuoteResponse.builder()
                .baseToPickupKm(Math.round(leg1 * 100.0) / 100.0)
                .pickupToDestinationKm(Math.round(leg2 * 100.0) / 100.0)
                .destinationToBaseKm(Math.round(leg3 * 100.0) / 100.0)
                .totalDistanceKm(totalDistanceKm)
                .estimatedCostZAR(Math.round(totalCost * 100.0) / 100.0)
                .currency("ZAR")
                .build();
    }

    private double simulateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula calculation for distance in KM
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1.3; // 1.3 road winding factor approximation
    }
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java`

```text
// backend/src/main/java/com/ssheavyhaulage/dto/QuoteResponse.java
package com.ssheavyhaulage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteResponse {
    private double baseToPickupKm;
    private double pickupToDestinationKm;
    private double destinationToBaseKm;
    private double totalDistanceKm;
    private double estimatedCostZAR;
    private String currency; // ZAR (R)
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/dto/QuoteRequest.java`

```text
// backend/src/main/java/com/ssheavyhaulage/dto/QuoteRequest.java
package com.ssheavyhaulage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuoteRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickupAddress;

    @NotBlank(message = "Destination location is required")
    private String destinationAddress;

    @NotNull(message = "Cargo weight is required")
    private Double cargoWeightTons; // Tonnage affects per-km multiplier
}
```

## File: `./backend/src/main/java/com/ssheavyhaulage/model/FleetVehicle.java`

```text
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

