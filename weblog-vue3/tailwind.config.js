/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.js"
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        ink: {
          50: '#f4f7f7',
          100: '#e4ecec',
          200: '#c9d9d9',
          300: '#a0bdbd',
          400: '#6f9898',
          500: '#4f7a7a',
          600: '#3d6161',
          700: '#334f4f',
          800: '#2c4242',
          900: '#273838',
          950: '#132020',
        },
        accent: {
          DEFAULT: '#0f766e',
          soft: '#ccfbf1',
          muted: '#14b8a6',
          dark: '#134e4a',
        },
        surface: {
          DEFAULT: '#ffffff',
          soft: '#f7f8fa',
          muted: '#eef0f3',
        },
      },
      fontFamily: {
        sans: ['"Noto Sans SC"', '"Outfit"', 'system-ui', 'sans-serif'],
        display: ['"Outfit"', '"Noto Sans SC"', 'system-ui', 'sans-serif'],
      },
      boxShadow: {
        soft: '0 1px 2px rgba(15, 23, 42, 0.04), 0 4px 16px rgba(15, 23, 42, 0.04)',
        lift: '0 8px 28px rgba(15, 23, 42, 0.08)',
      },
      maxWidth: {
        content: '72rem',
      },
      animation: {
        'fade-up': 'fadeUp 0.5s ease-out both',
      },
      keyframes: {
        fadeUp: {
          '0%': { opacity: '0', transform: 'translateY(12px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' },
        },
      },
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
