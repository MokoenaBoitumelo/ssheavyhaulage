import { useEffect, useState } from "react";
import { Truck, AlertCircle } from "lucide-react";
import { fetchAvailableFleet } from "../services/apiClient";
import { FleetVehicle } from "../types/haulage";

export function FleetShowcase() {
    const [vehicles, setVehicles] = useState<FleetVehicle[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        let active = true;

        fetchAvailableFleet()
            .then((data) => {
                if (active) {
                    setVehicles(data);
                    setError(null);
                }
            })
            .catch(() => {
                if (active) {
                    setError("Unable to load fleet catalog from the backend API.");
                }
            })
            .finally(() => {
                if (active) {
                    setLoading(false);
                }
            });

        return () => {
            active = false;
        };
    }, []);

    return (
        <section id="fleet" className="border-b border-haulage-steel bg-haulage-surface py-16 text-white">
            <div className="mx-auto max-w-6xl px-4">
                <h2 className="text-3xl font-bold">Heavy Haulage Fleet</h2>
                <p className="mt-2 text-sm text-slate-400">
                    Available rigs seeded from the SS Heavy Haulage dispatch catalog.
                </p>

                {loading && <p className="mt-8 text-sm text-slate-300">Loading available fleet rigs...</p>}

                {error && (
                    <div role="alert" className="mt-8 flex items-center gap-2 rounded border border-red-500/40 bg-red-950/40 p-4 text-sm text-red-200">
                        <AlertCircle className="h-4 w-4 shrink-0 text-red-400" />
                        <span>{error}</span>
                    </div>
                )}

                {!loading && !error && (
                    <div className="mt-8 grid gap-6 md:grid-cols-3">
                        {vehicles.map((rig) => (
                            <article
                                key={rig.id}
                                className="flex flex-col justify-between rounded-lg border border-haulage-steel bg-haulage-charcoal p-6"
                            >
                                <div>
                                    <div className="flex items-center justify-between gap-2">
                    <span className="inline-flex items-center gap-1.5 rounded bg-haulage-orange/15 px-2.5 py-1 text-xs font-semibold text-haulage-orange">
                      <Truck className="h-3.5 w-3.5" />
                      Max {rig.maxPayloadTons}t
                    </span>
                                        <span className="text-xs font-medium text-emerald-400">Available</span>
                                    </div>

                                    <h3 className="mt-4 text-xl font-bold text-white">{rig.name}</h3>
                                    <p className="mt-1 text-sm font-medium text-slate-400">{rig.trailerType}</p>
                                    <p className="mt-3 text-sm leading-relaxed text-slate-300">
                                        {rig.description ?? "Heavy industrial freight unit."}
                                    </p>
                                </div>
                            </article>
                        ))}
                    </div>
                )}
            </div>
        </section>
    );
}