import { Truck } from "lucide-react";

export function Navbar() {
    return (
        <header className="sticky top-0 z-30 border-b border-haulage-steel bg-haulage-charcoal/95 backdrop-blur">
            <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-4">
                <a href="#top" className="flex items-center gap-3 text-white">
          <span className="flex h-10 w-10 items-center justify-center rounded bg-haulage-orange text-haulage-charcoal">
            <Truck className="h-6 w-6" />
          </span>
                    <div>
                        <span className="block text-lg font-bold tracking-wide">SS HEAVY HAULAGE</span>
                        <span className="block text-xs text-slate-400">Kempton Park • Gauteng</span>
                    </div>
                </a>

                <nav aria-label="Primary Navigation" className="flex items-center gap-6 text-sm font-medium text-slate-300">
                    <a href="#fleet" className="hover:text-haulage-orange transition-colors">
                        Fleet
                    </a>
                    <a
                        href="#quote"
                        className="rounded bg-haulage-orange px-4 py-2 font-semibold text-haulage-charcoal hover:bg-haulage-orangeHover transition-colors"
                    >
                        Instant Quote
                    </a>
                </nav>
            </div>
        </header>
    );
}