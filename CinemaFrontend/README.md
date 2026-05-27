# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

## Frontend configuration for local backend

Copy `CinemaFrontend/.env.example` to `CinemaFrontend/.env` and keep the default values for local Docker backend (`https://localhost` and `wss://localhost/ws`) before running `npm run dev`.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh
