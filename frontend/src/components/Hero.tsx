import { MapPin, ShieldCheck } from "lucide-react";

export function Hero() {
    return (
        <section id="top" className="border-b border-haulage-steel bg-haulage-charcoal py-16 text-white">
            <div className="mx-auto max-w-6xl px-4">
                <div className="inline-flex items-center gap-2 rounded-full border border-haulage-orange/40 bg-haulage-orange/10 px-3 py-1 text-xs font-semibold text-haulage-orange">
                    <MapPin className="h-3.5 w-3.5" />
                    Base Hub: Kempton Park, South Africa
                </div>

                <h1 className="mt-5 max-w-3xl text-4xl font-extrabold tracking-tight sm:text-5xl">
                    Heavy Load Transport Engineered for <span className="text-haulage-orange">South African Corridors</span>
                </h1>

                <p className="mt-4 max-w-2xl text-base text-slate-300 sm:text-lg">
                    Superlinks, abnormal lowbeds, and tautliners dispatched from Kempton Park with transparent tri-leg route
                    estimation in ZAR.
                </p>

                <div className="mt-8 flex flex-wrap items-center gap-4">
                    <a
                        href="#quote"
                        className="rounded bg-haulage-orange px-6 py-3 text-sm font-bold text-haulage-charcoal hover:bg-haulage-orangeHover transition-colors"
                    >
                        Estimate Haulage Cost
                    </a>
                    <a
                        href="#fleet"
                        className="rounded border border-haulage-steel bg-haulage-surface px-6 py-3 text-sm font-semibold text-white hover:border-slate-500 transition-colors"
                    >
                        Explore Heavy Fleet
                    </a>
                    <span className="inline-flex items-center gap-2 text-xs text-slate-400">
            <ShieldCheck className="h-4 w-4 text-haulage-orange" />
            Tri-leg (Base → Pickup → Destination → Base) calculation
          </span>
                </div>
            </div>
        </section>
    );
}