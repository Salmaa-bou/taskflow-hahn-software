/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#ecfeff',
          100: '#cffafe',
          200: '#a5f3fc',
          300: '#67e8f9',
          400: '#22d3ee',
          500: '#0ea5e9',
          600: '#0284c7',
          700: '#0369a1',
          800: '#075985',
          900: '#0b4f6c',
          950: '#082f49'
        },
        ink: {
          50: '#111827',
          100: '#0f1625',
          200: '#0c1220',
          300: '#0b101d',
          400: '#090e1a',
          500: '#070c17',
          600: '#060a14',
          700: '#040811',
          800: '#03070f',
          900: '#02060d'
        },
      },
    },
  },
  plugins: [],
}
