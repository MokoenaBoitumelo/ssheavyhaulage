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
   ├── index.html
   ├── package.json
   ├── postcss.config.js
   ├── tailwind.config.js
   ├── tsconfig.json
   └── src/
      ├── types/
      │   └── haulage.ts          # FleetVehicle, QuoteRequest, QuoteResponse interfaces
      ├── services/
      │   └── apiClient.ts        # Axios client wired to http://localhost:8080/api/v1
      ├── components/
      │   ├── Navbar.tsx          # Brand header & section navigation
      │   ├── Hero.tsx            # SS Heavy Haulage Kempton Park hub hero section
      │   ├── FleetShowcase.tsx   # Fetches & renders /api/v1/fleet vehicles
      │   └── QuoteEstimator.tsx  # Tri-leg distance & ZAR quote calculator form
      ├── App.tsx                 # Main layout assembling the end-user portal
      └── main.tsx                # Application entry point & Tailwind CSS import
      


### Project Features
Fleet Showcase: Dynamic catalog of heavy-haul rigs and specialized low-beds.

Tri-Leg Distance Estimator: Computes travel distances (Base → Pickup → Destination → Base).

Instant Cost Calculation: Transparent ZAR rate estimation based on load weight and distance.