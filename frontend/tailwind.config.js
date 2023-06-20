/** @type {import('tailwindcss').Config} */
module.exports = 
{
  content: 
  [
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: 
  {
    extend: 
    {
      colors: 
      {
        brand: 
        {
          100: '#8ec1c2',
          200: '#04bf8a',
          300: '#03a64a',
          400: '#025940',
          500: '#018c65',
        }
      },

      fontFamily:
      {
        'main': ['Roboto', 'sans-serif'],
        'title': ['Fugaz One', 'sans-serif']
      }

    },


  },
  plugins: [],
}