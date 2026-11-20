// frontend/src/components/QuoteEstimator.test.tsx
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { QuoteEstimator } from "./QuoteEstimator";
import * as apiClient from "../services/apiClient";
import * as locationLookup from "../services/locationLookup";

vi.mock("../services/apiClient");
vi.mock("../services/locationLookup");

describe("QuoteEstimator", () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("shows a validation alert when pickup or destination is empty", async () => {
        const user = userEvent.setup();
        render(<QuoteEstimator />);

        await user.click(screen.getByRole("button", { name: /calculate estimate/i }));

        expect(await screen.findByRole("alert")).toHaveTextContent(
            /please enter both a pickup location and a destination/i
        );
        expect(apiClient.calculateHaulageQuote).not.toHaveBeenCalled();
    });

    it("renders tri-leg distances and formatted ZAR cost on a valid quote", async () => {
        const user = userEvent.setup();
        vi.mocked(apiClient.calculateHaulageQuote).mockResolvedValue({
            baseToPickupKm: 10,
            pickupToDestinationKm: 570,
            destinationToBaseKm: 580,
            totalDistanceKm: 1160,
            estimatedCostZAR: 79800,
            currency: "ZAR",
        });

        render(<QuoteEstimator />);

        await user.type(screen.getByLabelText(/pickup location/i), "Kempton Park");
        await user.type(screen.getByLabelText(/destination location/i), "Durban Harbour");
        await user.clear(screen.getByLabelText(/cargo weight/i));
        await user.type(screen.getByLabelText(/cargo weight/i), "20");
        await user.click(screen.getByRole("button", { name: /calculate estimate/i }));

        await waitFor(() => {
            expect(screen.getByText("1160.00 km")).toBeInTheDocument();
        });
        expect(screen.getByText(/ZAR\s+79[\s\u00a0\u202f]?800[,.]00/)).toBeInTheDocument();
    });

    it("populates address when a location suggestion is selected", async () => {
        const user = userEvent.setup();
        vi.mocked(locationLookup.searchSouthAfricanLocations).mockResolvedValue([
            {
                displayName: "Isando, Kempton Park, Gauteng, South Africa",
                lat: "-26.1384",
                lon: "28.2571",
            },
        ]);

        render(<QuoteEstimator />);

        await user.type(screen.getByLabelText(/pickup location/i), "Isando");
        const option = await screen.findByRole("button", {
            name: /isando, kempton park, gauteng, south africa/i,
        });
        await user.click(option);

        expect(screen.getByLabelText(/pickup location/i)).toHaveValue(
            "Isando, Kempton Park, Gauteng, South Africa"
        );
    });

    it("displays backend error message when location cannot be resolved", async () => {
        const user = userEvent.setup();
        const axiosError = Object.assign(new Error("Bad Request"), {
            isAxiosError: true,
            response: {
                data: { error: "Could not resolve South African location: UnknownPlace" },
            },
        });
        vi.mocked(apiClient.calculateHaulageQuote).mockRejectedValue(axiosError);

        render(<QuoteEstimator />);

        await user.type(screen.getByLabelText(/pickup location/i), "UnknownPlace");
        await user.type(screen.getByLabelText(/destination location/i), "Durban");
        await user.click(screen.getByRole("button", { name: /calculate estimate/i }));

        expect(await screen.findByRole("alert")).toHaveTextContent(
            /could not resolve south african location: unknownplace/i
        );
    });
});