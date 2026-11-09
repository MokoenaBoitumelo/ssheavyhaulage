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