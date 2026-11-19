/** @type {import('tailwindcss').Config} */
export default {
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {
            colors: {
                haulage: {
                    charcoal: "#111317",
                    surface: "#1B1F26",
                    steel: "#2A303C",
                    orange: "#F97316",
                    orangeHover: "#EA580C",
                },
            },
        },
    },
    plugins: [],
};