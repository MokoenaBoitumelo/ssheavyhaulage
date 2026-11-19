export interface FleetVehicle {
    id: number;
    name: string;
    trailerType: string;
    maxPayloadTons: number;
    description: string | null;
    imageUrl: string | null;
    available: boolean;
}

export interface QuoteRequest {
    pickupAddress: string;
    destinationAddress: string;
    cargoWeightTons: number;
}

export interface QuoteResponse {
    baseToPickupKm: number;
    pickupToDestinationKm: number;
    destinationToBaseKm: number;
    totalDistanceKm: number;
    estimatedCostZAR: number;
    currency: string;
}