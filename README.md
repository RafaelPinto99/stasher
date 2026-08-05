# Stasher

Web app for tracking job applications, with a Chrome extension as the key differentiator: a "Save" button on a job posting page (LinkedIn, in the MVP) automatically captures the data and pre-fills the application in the app.

## Stack

- **Backend:** Spring Boot, PostgreSQL, Flyway, JWT (access + refresh token)
- **Frontend:** Next.js, TypeScript, Tailwind CSS, TanStack Query, Axios
- **Chrome extension:** Manifest V3, React + TypeScript, Shadow DOM
- **Testing:** JUnit + Mockito + Testcontainers (backend), Vitest + React Testing
  Library (frontend), Playwright (E2E)
- **CI/CD:** GitHub Actions
- **Hosting:** Vercel (frontend), Render (backend), Neon (PostgreSQL)

## Architecture

Modular monolith - a single deployment process, internally split into domain
modules, each following a Controller → Service → Repository layering.

## Repository structure

```
stasher/
├── backend/            Spring Boot (Maven)
├── frontend/           Next.js
├── extension/          Chrome extension (Manifest V3)
├── packages/
│   └── shared-types/   TypeScript types shared between frontend and extension
└── .github/workflows/  CI/CD
```

## Status

Early development.
