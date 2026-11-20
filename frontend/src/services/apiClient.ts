import axios from "axios";
import { FleetVehicle, QuoteRequest, QuoteResponse } from "../types/haulage";

// Configure base API URL via VITE_API_BASE_URL environment variable when deploying
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api/v1";

const httpClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
    timeout: 15000,
});

export async function fetchAvailableFleet(): Promise<FleetVehicle[]> {
    const { data } = await httpClient.get<FleetVehicle[]>("/fleet");
    return data;
}

export async function calculateHaulageQuote(payload: QuoteRequest): Promise<QuoteResponse> {
    const { data } = await httpClient.post<QuoteResponse>("/quotes/calculate", payload);
    return data;
}