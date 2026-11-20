// frontend/src/components/LocationAutocompleteInput.tsx
import { useEffect, useRef, useState } from "react";
import {
    LocationSuggestion,
    searchSouthAfricanLocations,
} from "../services/locationLookup";

interface LocationAutocompleteInputProps {
    id: string;
    label: string;
    placeholder: string;
    value: string;
    onChange: (nextValue: string) => void;
}

export function LocationAutocompleteInput({
                                              id,
                                              label,
                                              placeholder,
                                              value,
                                              onChange,
                                          }: LocationAutocompleteInputProps) {
    const [suggestions, setSuggestions] = useState<LocationSuggestion[]>([]);
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const skipNextFetchRef = useRef<boolean>(false);
    const containerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        // Skip re-querying immediately after the user selects a suggestion
        if (skipNextFetchRef.current) {
            skipNextFetchRef.current = false;
            return;
        }

        const trimmed = value.trim();
        if (trimmed.length < 3) {
            setSuggestions([]);
            setIsOpen(false);
            return;
        }

        const controller = new AbortController();
        const timerId = window.setTimeout(async () => {
            setLoading(true);
            try {
                const results = await searchSouthAfricanLocations(trimmed, controller.signal);
                if (!controller.signal.aborted) {
                    setSuggestions(results);
                    setIsOpen(results.length > 0);
                }
            } catch {
                if (!controller.signal.aborted) {
                    setSuggestions([]);
                    setIsOpen(false);
                }
            } finally {
                if (!controller.signal.aborted) {
                    setLoading(false);
                }
            }
        }, 400);

        return () => {
            window.clearTimeout(timerId);
            controller.abort();
        };
    }, [value]);

    useEffect(() => {
        function handleOutsideClick(event: MouseEvent) {
            if (containerRef.current && !containerRef.current.contains(event.target as Node)) {
                setIsOpen(false);
            }
        }

        document.addEventListener("mousedown", handleOutsideClick);
        return () => document.removeEventListener("mousedown", handleOutsideClick);
    }, []);

    function handleSelect(suggestion: LocationSuggestion) {
        skipNextFetchRef.current = true;
        onChange(suggestion.displayName);
        setSuggestions([]);
        setIsOpen(false);
    }

    return (
        <div ref={containerRef} className="relative">
            <label htmlFor={id} className="block text-sm font-medium text-slate-200">
                {label}
            </label>
            <input
                id={id}
                type="text"
                required
                autoComplete="off"
                placeholder={placeholder}
                value={value}
                onFocus={() => {
                    if (suggestions.length > 0) setIsOpen(true);
                }}
                onChange={(e) => onChange(e.target.value)}
                className="mt-1.5 w-full rounded border border-haulage-steel bg-haulage-charcoal px-3.5 py-2.5 text-sm text-white placeholder-slate-500 focus:border-haulage-orange focus:outline-none"
            />
            {loading && (
                <span className="mt-1 block text-xs text-slate-400">Searching South African locations...</span>
            )}
            {isOpen && suggestions.length > 0 && (
                <ul
                    role="listbox"
                    aria-label={`${label} suggestions`}
                    className="absolute z-20 mt-1 max-h-56 w-full overflow-y-auto rounded border border-haulage-steel bg-haulage-charcoal py-1 shadow-lg"
                >
                    {suggestions.map((item) => (
                        <li key={`${item.lat}-${item.lon}-${item.displayName}`}>
                            <button
                                type="button"
                                onClick={() => handleSelect(item)}
                                className="w-full px-3.5 py-2 text-left text-xs text-slate-200 hover:bg-haulage-surface hover:text-haulage-orange transition-colors"
                            >
                                {item.displayName}
                            </button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}