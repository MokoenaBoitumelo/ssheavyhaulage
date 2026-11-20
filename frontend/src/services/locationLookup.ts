// frontend/src/services/locationLookup.ts
export interface LocationSuggestion {
    displayName: string;
    lat: string;
    lon: string;
}

/**
 * Queries OpenStreetMap Nominatim restricted to South Africa (countrycodes=za).
 * Uses AbortSignal so stale in-flight requests are cancelled as the user types.
 */
export async function searchSouthAfricanLocations(
    query: string,
    signal?: AbortSignal
): Promise<LocationSuggestion[]> {
    const trimmed = query.trim();
    if (trimmed.length < 3) {
        return [];
    }

    const params = new URLSearchParams({
        q: trimmed,
        countrycodes: "za",
        format: "json",
        limit: "5",
    });

    const response = await fetch(
        `https://nominatim.openstreetmap.org/search?${params.toString()}`,
        {
            signal,
            headers: {
                Accept: "application/json",
            },
        }
    );

    if (!response.ok) {
        return [];
    }

    const raw: Array<{ display_name?: string; lat?: string; lon?: string }> =
        await response.json();

    return raw
        .filter((item) => item.display_name && item.lat && item.lon)
        .map((item) => ({
            displayName: item.display_name as string,
            lat: item.lat as string,
            lon: item.lon as string,
        }));
}