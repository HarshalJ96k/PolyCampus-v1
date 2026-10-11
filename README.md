# PolyCampus

**A college management app for Government Polytechnic Murtizapur (MSBTE).**
Attendance, timetable, notices, leave, marks and document requests, on Android and a web admin panel.

![Status](https://img.shields.io/badge/status-planning%20complete-blue)
![Platform](https://img.shields.io/badge/platform-Android%20%2B%20Web-green)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot%20%2B%20PostgreSQL-orange)

> **Project status:** the planning and design documents are finished (see [`docs/`](docs/)). Implementation has not started yet. Sections marked *planned* describe what will exist, not what exists today.

---

## Why PolyCampus

The college runs attendance, leave, notices, timetables, marks and certificate requests on paper. That causes lost forms, slow approvals, no easy view of a student's attendance percentage, and repeated manual work every semester.

PolyCampus replaces this with one system built for a single college:

- Teachers take **per-lecture attendance in under a minute**, including practical batches, even with no internet.
- Students see their **attendance, timetable, marks and notices** on their phone.
- HODs and the Principal get **department and college overviews** and approve leave.
- The Students Section tracks **certificate and receipt requests** from one queue.
- The Admin configures the rules, because the college has no written rule set yet.

## Features

| Module | What it does | Release |
|---|---|---|
| Accounts and roles | Login with enrollment number or staff ID, forced password change, accounts created by the Admin, suspension, audit trail | 1.0 |
| Academic structure | Departments, years, terms, classes, practical batches, subjects, teacher assignments, semester wizard, student promotion, CSV import | 1.0 |
| Timetable | Periods, weekly slots for theory and practicals, conflict detection, substitutions, holidays | 1.0 |
| Attendance | Per lecture and per subject, batch-aware, offline capture with automatic sync, same-day edit by the conducting teacher, percentages, defaulters, reports | 1.0 |
| Notices | Role-based targeting (college, department, class), attachments, push notifications | 1.0 |
| Documents | Bonafide, fee receipt and other requests with status tracking (no PDF generation) | 1.1 |
| Leave | Students, teachers and HODs; configurable approval chain | 1.1 |
| Marks | Two unit tests and practical marks: entry and viewing only | 1.2 |

Out of scope for version 1: online fee payment, certificate PDF generation, parent accounts, iOS, multi-college support.

## Roles

Student, Teacher (including class teacher and substitute), HOD, Principal, College Admin, Students Section. One person can hold several roles. All access rules are enforced on the server.

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot, Spring Security, Spring Data JPA, Flyway |
| Database | PostgreSQL 16+ |
| Android app | Kotlin, Jetpack Compose (Material 3), Hilt, Retrofit, Room, WorkManager |
| Admin web panel | React, TypeScript, Vite, TanStack Query, Material UI |
| Push notifications | Firebase Cloud Messaging (notifications only) |
| Hosting | One Linux VPS with Docker Compose and Caddy (automatic HTTPS) |
| CI/CD | GitHub Actions, GitHub Container Registry |
| Backups | restic to encrypted off-site storage |

## Architecture

```
 Android app (Kotlin)        Admin web panel (React)
          \                        /
           \------ HTTPS / JSON --/
                     |
           Caddy reverse proxy (TLS)
                     |
     Spring Boot API  /api/v1  (modular monolith)
     identity | academics | timetable | attendance | marks
     notices | leave | documents | notifications | audit | files
            |                      |                |
       PostgreSQL        private file volume   Firebase Cloud Messaging
```

Key decisions: one modular monolith, one database, own authentication (short-lived JWT plus rotating refresh tokens), files behind authorised endpoints, and the notifications table doubling as the push outbox. See the [Technical Requirements Document](docs/2_PolyCampus_Technical_Requirements_Document.docx) for the reasoning.

## Repository structure (planned)

```
polycampus/
  backend/        Spring Boot API (Gradle), Flyway migrations
  admin-web/      React + TypeScript admin panel
  android-app/    Kotlin + Compose app
  infra/          docker-compose.yml, Caddyfile, backup scripts, runbooks
  docs/           planning documents and schema
  .github/        CI and release workflows
```

## Documentation

All design documents are in [`docs/`](docs/):

| # | Document | Contents |
|---|---|---|
| 1 | [Project Requirements](docs/1_PolyCampus_Project_Requirements_Document.docx) | Goals, roles, permissions, functional requirements, business rules, risks, open questions |
| 2 | [Technical Requirements](docs/2_PolyCampus_Technical_Requirements_Document.docx) | Architecture, security design, API catalogue, algorithms, deployment, testing |
| 3 | [UI/UX Design](docs/3_PolyCampus_UI_UX_Design_Document.docx) | Design system, screen specifications, wireframes, accessibility |
| 4 | [Database Schema](docs/4_PolyCampus_Database_Schema_Design.docx) | 48 tables, constraints, indexes, sample queries, full DDL |
| 5 | [Implementation Plan](docs/5_PolyCampus_Project_Implementation_Plan.docx) | Weekly plan, milestones, testing, training, handover |
| 6 | [App Flow and Navigation](docs/6_PolyCampus_App_Flow_and_Navigation_Document.docx) | Navigation per role, routes, deep links, task flows |

The executable schema is [`schema.sql`](schema.sql), to become the Flyway baseline `V1__baseline.sql`.

## Getting started (planned)

The commands below describe the intended set-up and will be finalised when the code exists.

**Requirements:** JDK 21, Node.js (current LTS), Android Studio, Docker.

```bash
# 1. Start PostgreSQL locally
docker compose -f infra/docker-compose.dev.yml up -d

# 2. Run the API (applies Flyway migrations on start)
cd backend && ./gradlew bootRun

# 3. Run the admin panel
cd admin-web && npm install && npm run dev

# 4. Open android-app/ in Android Studio and run on an emulator or phone
```

Local development uses **synthetic data only**. A generator for a realistic fake college (5 departments, 15 classes, about 1,500 students) is part of the plan.

## Roadmap

Planned from 19 October 2026 with three developers at about one hour a day each. Dates are indicative.

| Milestone | Target week | Outcome |
|---|---|---|
| M1 Platform ready | 11 | Login, users, CI/CD, staging, backups with a tested restore |
| M2 Structure ready | 19 | A department set up through the wizard; timetable visible |
| M3 Attendance alpha | 29 | Attendance with offline sync used by friendly teachers |
| M4 Notices live | 34 | Role-targeted notices with push on 5+ phone models |
| **M5 Release 1.0** | 40 | Core release piloted in one department |
| M6 Release 1.1 | 51 | Documents and leave |
| M7 Release 1.2 | 57 | Marks |
| M8 Handover | 62 | Whole college live; maintainers trained |

Details are in the [Implementation Plan](docs/5_PolyCampus_Project_Implementation_Plan.docx).

## Security and privacy

- Authorisation is checked on the server for every request, with scope rules per role (own, class, department).
- Passwords are stored as BCrypt hashes; refresh tokens rotate and are revoked on logout, reset or suspension.
- Uploaded files are private and downloadable only by authorised users.
- Push messages never contain marks, attendance figures, leave reasons or other private data.
- Sensitive actions are written to an append-only audit log.
- **No real student data is used until the college approves in writing and a data-handling agreement is signed.** Many first-year students are under 18, so consent requirements must be confirmed with the college.

Please report security issues privately to the maintainers (see Contact) and not in public issues.

## Contributing

1. Pick an issue linked to a requirement ID (for example `ATT-04`) from the project board.
2. Branch from `main` (`feature/ATT-04-submit-attendance`).
3. Keep changes small, add or update tests (including authorisation tests), and open a pull request.
4. At least one reviewer other than the author must approve, and CI must pass.

Code style is enforced in CI. The full workflow and definition of done are in the Technical Requirements Document, sections 18 and 20.

## Team

PolyCampus is built by three developers for Government Polytechnic Murtizapur. Add names, roles and links here.

## License

To be decided with the college before the first release. Until a license file is added, all rights are reserved.

## Contact

- Project maintainers: *add email or GitHub handles*
- College contact: *add name and role after confirmation*
