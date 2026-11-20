import { FormEvent, useState } from "react";
import { Calculator, AlertCircle } from "lucide-react";
import axios from "axios";
import { calculateHaulageQuote } from "../services/apiClient";
import { QuoteResponse } from "../types/haulage";
import { LocationAutocompleteInput } from "./LocationAutocompleteInput";

export function QuoteEstimator() {
    const [pickupAddress, setPickupAddress] = useState<string>("");
    const [destinationAddress, setDestinationAddress] = useState<string>("");
    const [cargoWeightTons, setCargoWeightTons] = useState<string>("15");
    const [quote, setQuote] = useState<QuoteResponse | null>(null);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setErrorMessage(null);

        const parsedWeight = Number(cargoWeightTons);
        if (!pickupAddress.trim() || !destinationAddress.trim()) {
            setErrorMessage("Please enter both a pickup location and a destination in South Africa.");
            return;
        }
        if (!Number.isFinite(parsedWeight) || parsedWeight <= 0) {
            setErrorMessage("Cargo weight must be greater than 0 tons.");
            return;
        }

        setSubmitting(true);
        try {
            const result = await calculateHaulageQuote({
                pickupAddress: pickupAddress.trim(),
                destinationAddress: destinationAddress.trim(),
                cargoWeightTons: parsedWeight,
            });
            setQuote(result);
        } catch (err) {
            setQuote(null);
            if (axios.isAxiosError(err) && err.response?.data) {
                const data = err.response.data as Record<string, string>;
                const firstMsg = data.error ?? Object.values(data)[0];
                setErrorMessage(firstMsg ?? "Unable to calculate quote. Check your route details.");
            } else {
                setErrorMessage("Could not reach the quote service. Ensure the backend is running on port 8080.");
            }
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <section id="quote" className="bg-haulage-charcoal py-16 text-white">
            <div className="mx-auto max-w-6xl px-4">
                <div className="flex items-center gap-2 text-haulage-orange">
                    <Calculator className="h-5 w-5" />
                    <span className="text-xs font-bold uppercase tracking-wider">Instant Route & Rate Estimator</span>
                </div>
                <h2 className="mt-2 text-3xl font-bold">Tri-Leg Haulage Quote</h2>
                <p className="mt-2 text-sm text-slate-400">
                    Calculated from our Kempton Park depot across Base → Pickup → Destination → Base in South African Rand (ZAR).
                </p>

                <div className="mt-8 grid gap-8 lg:grid-cols-2">
                    <form
                        onSubmit={handleSubmit}
                        noValidate
                        className="space-y-4 rounded-lg border border-haulage-steel bg-haulage-surface p-6"

                    >
                        <LocationAutocompleteInput
                            id="pickup"
                            label="Pickup Location (South Africa)"
                            placeholder="e.g., Isando, Kempton Park"
                            value={pickupAddress}
                            onChange={setPickupAddress}
                        />

                        <LocationAutocompleteInput
                            id="destination"
                            label="Destination Location (South Africa)"
                            placeholder="e.g., Durban Harbour"
                            value={destinationAddress}
                            onChange={setDestinationAddress}
                        />

                        <div>
                            <label htmlFor="pickup" className="block text-sm font-medium text-slate-200">
                                Pickup Location (South Africa)
                            </label>
                            <input
                                id="pickup"
                                type="text"
                                required
                                placeholder="e.g., Isando, Kempton Park"
                                value={pickupAddress}
                                onChange={(e) => setPickupAddress(e.target.value)}
                                className="mt-1.5 w-full rounded border border-haulage-steel bg-haulage-charcoal px-3.5 py-2.5 text-sm text-white placeholder-slate-500 focus:border-haulage-orange focus:outline-none"
                            />
                        </div>

                        <div>
                            <label htmlFor="destination" className="block text-sm font-medium text-slate-200">
                                Destination Location (South Africa)
                            </label>
                            <input
                                id="destination"
                                type="text"
                                required
                                placeholder="e.g., Durban Harbour"
                                value={destinationAddress}
                                onChange={(e) => setDestinationAddress(e.target.value)}
                                className="mt-1.5 w-full rounded border border-haulage-steel bg-haulage-charcoal px-3.5 py-2.5 text-sm text-white placeholder-slate-500 focus:border-haulage-orange focus:outline-none"
                            />
                        </div>

                        <div>
                            <label htmlFor="weight" className="block text-sm font-medium text-slate-200">
                                Cargo Weight (Metric Tons)
                            </label>
                            <input
                                id="weight"
                                type="number"
                                step="0.1"
                                min="0.1"
                                required
                                value={cargoWeightTons}
                                onChange={(e) => setCargoWeightTons(e.target.value)}
                                className="mt-1.5 w-full rounded border border-haulage-steel bg-haulage-charcoal px-3.5 py-2.5 text-sm text-white focus:border-haulage-orange focus:outline-none"
                            />
                        </div>

                        {errorMessage && (
                            <div
                                role="alert"
                                className="flex items-center gap-2 rounded border border-red-500/40 bg-red-950/40 p-3 text-xs text-red-200"
                            >
                                <AlertCircle className="h-4 w-4 shrink-0 text-red-400" />
                                <span>{errorMessage}</span>
                            </div>
                        )}

                        <button
                            type="submit"
                            disabled={submitting}
                            className="w-full rounded bg-haulage-orange px-5 py-3 text-sm font-bold text-haulage-charcoal hover:bg-haulage-orangeHover disabled:opacity-60 transition-colors"
                        >
                            {submitting ? "Calculating Route..." : "Calculate Estimate"}
                        </button>
                    </form>

                    <div className="rounded-lg border border-haulage-steel bg-haulage-surface p-6">
                        <h3 className="text-lg font-bold text-white">Estimate Breakdown</h3>

                        {!quote ? (
                            <p className="mt-4 text-sm text-slate-400">
                                Submit a pickup point, destination, and cargo weight to view leg-by-leg distances and total ZAR estimate.
                            </p>
                        ) : (
                            <dl className="mt-4 space-y-3 text-sm">
                                <div className="flex justify-between border-b border-haulage-steel pb-2">
                                    <dt className="text-slate-400">Base → Pickup</dt>
                                    <dd className="font-semibold text-white">{quote.baseToPickupKm.toFixed(2)} km</dd>
                                </div>
                                <div className="flex justify-between border-b border-haulage-steel pb-2">
                                    <dt className="text-slate-400">Pickup → Destination</dt>
                                    <dd className="font-semibold text-white">{quote.pickupToDestinationKm.toFixed(2)} km</dd>
                                </div>
                                <div className="flex justify-between border-b border-haulage-steel pb-2">
                                    <dt className="text-slate-400">Destination → Base</dt>
                                    <dd className="font-semibold text-white">{quote.destinationToBaseKm.toFixed(2)} km</dd>
                                </div>
                                <div className="flex justify-between border-b border-haulage-steel pb-2">
                                    <dt className="text-slate-400">Total Tri-Leg Distance</dt>
                                    <dd className="font-bold text-haulage-orange">{quote.totalDistanceKm.toFixed(2)} km</dd>
                                </div>
                                <div className="flex items-baseline justify-between pt-2">
                                    <dt className="text-base font-bold text-white">Estimated Total</dt>
                                    <dd className="text-2xl font-extrabold text-haulage-orange">
                                        {quote.currency} {quote.estimatedCostZAR.toLocaleString("en-ZA", { minimumFractionDigits: 2 })}
                                    </dd>
                                </div>
                            </dl>
                        )}
                    </div>
                </div>
            </div>
        </section>
    );
}