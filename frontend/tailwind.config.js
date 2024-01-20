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
          100: '#8ec1c2', // fundo menu
          200: '#04bf8a', // destaque
          300: '#03a64a', // fundo botões
          400: '#025940', // hover botões
          500: '#018c65', // hover nav menu
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