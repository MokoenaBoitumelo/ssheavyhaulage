# SS Heavy Haulage — Enterprise Logistics & Quote Portal

Full-stack heavy load transport web application and instant tri-leg quote calculator for **SS Heavy Haulage**, headquartered in **Kempton Park, Gauteng, South Africa**.

## Tech Stack

- **Backend:** Java 25/26, Spring Boot 3.5.7, Spring Data JPA, Jakarta Bean Validation, H2 (local dev) / PostgreSQL (production), Maven, JUnit 5, Mockito
- **Frontend:** React, TypeScript, Vite, Tailwind CSS, Axios, Lucide Icons, Vitest, React Testing Library
- **Routing & Geocoding:** OpenStreetMap Nominatim (`countrycodes=za`) & OSRM Driving Distance API (with Haversine road-curvature fallback)

---

## Project Architecture & Folder Structure


```text
ssheavyhaulage/
├── backend/                                        # Spring Boot 3.5.7 REST API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ssheavyhaulage/
│   │   │   │   ├── config/                         # AppConfig (CORS/RestClient), DataSeeder, GlobalExceptionHandler
│   │   │   │   ├── controller/                     # FleetController & QuoteController (/api/v1)
│   │   │   │   ├── dto/                            # Java records (Coordinates, QuoteRequest, QuoteResponse)
│   │   │   │   ├── model/                          # JPA entities (FleetVehicle)
│   │   │   │   ├── repository/                     # Spring Data JPA repositories
│   │   │   │   └── service/                        # RoutingService, OsrmRoutingService, QuoteCalculatorService
│   │   │   └── resources/
│   │   │       └── application.yml                 # Server port, H2 datasource, Kempton Park hub & ZAR rates
│   │   └── test/java/com/ssheavyhaulage/           # JUnit 5 & MockMvc unit/slice tests
│   └── pom.xml
│
├── frontend/                                       # React + TypeScript + Vite SPA
│   ├── src/
│   │   ├── components/                             # Navbar, Hero, FleetShowcase, QuoteEstimator, LocationAutocompleteInput
│   │   ├── services/                               # apiClient.ts (Axios), locationLookup.ts (Nominatim)
│   │   ├── types/                                  # TypeScript contracts mirroring backend DTOs
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── setupTests.ts
│   ├── package.json
│   ├── tailwind.config.js
│   └── vite.config.ts
│
├── Architectural_approach.md
└── README.md
```



### Core Domain Logic & Pricing Model
1. Tri-Leg Route Calculation: Every quote computes driving distance across three legs starting and ending at the Kempton Park Depot (-26.1076, 28.2293):
   $$\text{Total Distance} = D_{\text{Base} \to \text{Pickup}} + D_{\text{Pickup} \to \text{Destination}} + D_{\text{Destination} \to \text{Base}}$$   
   
2. ZAR Cost Formula:

   . Base Flat Fee: 1,500.00 ZAR   
   . Per-Kilometer Rate: 45.00 ZAR/km   
   . Weight Surcharge: 5% multiplier increase per metric ton exceeding the 10-ton standard base load:
   $$\text{Weight Factor} = 1.0 + \max(0, \text{CargoTons} - 10) \times 0.05$$   
   $$\text{Estimated Cost (ZAR)} = 1500 + (\text{Total Distance} \times 45 \times \text{Weight Factor})$$   
   
### Getting Started
Prerequisites:

   . JDK 25 or 26 (JAVA_HOME configured) and Maven 3.9+
   . Node.js 20+ and npm
   
1. Run the Backend (http://localhost:8080):

         Bash
            cd backend
            mvn clean test
            mvn spring-boot:run
   
      Fleet Endpoint: GET http://localhost:8080/api/v1/fleet   
      Quote Endpoint: POST http://localhost:8080/api/v1/quotes/calculate   
      H2 Console (Local Dev): http://localhost:8080/h2-console (jdbc:h2:mem:sshaulagedb)[cite: 1]
      
   Security Note: Disable /h2-console (spring.h2.console.enabled=false) and supply PostgreSQL credentials and VITE_API_BASE_URL via environment variables before deploying to production[cite: 1].
   
2. Run the Frontend (http://localhost:5173):

         Bash
         cd frontend
         npm install
         npx vitest run
         npm run dev

(Optional) Create frontend/.env to override the backend API URL:

      Code snippet
      VITE_API_BASE_URL=http://localhost:8080/api/v1


### Automated Testing

   Backend (JUnit 5, Mockito, MockMvc):

      Bash
      cd backend && mvn test
   
   Covers QuoteCalculatorServiceTest, QuoteControllerTest, and FleetControllerTest[cite: 1].
   
   Frontend (Vitest & React Testing Library):

      Bash
      cd frontend && npx vitest run
   
   Covers form validation, debounced South African location selection, ZAR quote breakdown rendering, and API error alerts.