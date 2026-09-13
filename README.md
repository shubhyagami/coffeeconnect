# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that pairs coworkers for quick 15‑minute coffee chats.  
Users mark themselves as **“Open for Coffee”**, then the system matches them with another available colleague.  
They can exchange text, images, and voice notes, and start a WebRTC video call.  
An optional admin dashboard (`/admin`) displays users, active sessions, and a few basic metrics.

---

## ⏱️ Badges

[![Build](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?branch=main&label=build&logo=github)](https://github.com/shubhyagami/coffeeconnect/actions)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)](https://www.postgresql.org/)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-Compose-2496ED)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](CONTRIBUTING.md)

---

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Local Development](#local-development)
  - [Docker](#docker)
- [Configuration](#configuration)
- [Usage](#usage)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

---

## Overview

| Item | Detail |
|------|--------|
| Framework | Spring Boot 3.4.4 |
| Language | Java 21 |
| Database | PostgreSQL (or any JDBC‑compatible DB); in‑memory H2 is used by default |
| UI | Thymeleaf templates + Bootstrap 5 |
| Real‑time | WebSocket/STOMP via SockJS |
| Video | Peer‑to‑peer WebRTC, signaled over WebSocket |
| Packaging | Single executable JAR (no external services required) |

---

## Features

- **Matchmaking** – Users flagged as “Open for Coffee” are queued and paired automatically within 15 minutes.
- **Interest Tags** – Add tags (e.g., *Java*, *Coffee Brewing*) to improve match relevance.
- **Real‑time Chat** – Send text, images, and voice notes with WebSocket/STOMP.
- **WebRTC Video Call** – Start a low‑latency peer‑to‑peer video session.
- **Admin Dashboard** – `/admin` shows user lists, active sessions, and simple metrics.
- **Hot‑reloading** – Spring Boot DevTools enables instant code changes during development.

---

## Getting Started

### Prerequisites

- JDK 21
- PostgreSQL (or any JDBC‑compatible DB) – optional for local development
- Maven 3.9+  
- Docker – optional

### Local Development

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

Open <http://localhost:8080>.  
If no external database is configured, the app uses an in‑memory H2 database for quick starts.

### Docker

```bash
docker build -t coffeeconnect:latest .
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

Create a `.env` file in the project root with the environment variables shown in the *Configuration* section below.

---

## Configuration

CoffeeConnect reads settings from environment variables or `src/main/resources/application.yml`.  
Variables with defaults can be overridden in `.env` or `application.yml`.

| Variable           | Description           | Default |
|--------------------|----------------------|---------|
| `DATASOURCE_URL`   | JDBC URL             | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB user         | `postgres` |
| `DATASOURCE_PASSWORD` | DB password       | `postgres` |
| `ADMIN_USERNAME`   | Admin login          | `admin` |
| `ADMIN_PASSWORD`   | Admin password       | `admin` |

Example `.env`:

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

Additional Spring configuration can be added to `application.yml`.

---

## Usage

1. Log in as a regular user or the admin.  
2. Mark your status as **“Open for Coffee”** to enter the matchmaking queue.  
3. Add at least three interest tags to help the system find a good match.  
4. When a match is found, a notification appears.  
5. Use the chat panel to send text, images, or voice notes.  
6. Click **Video Call** to start a WebRTC session.  
7. Admins can visit `/admin` for user lists, active sessions, and metrics.

---

## Development

```bash
# Run tests
mvn test

# Format code
mvn fmt:format
```

The project follows standard Maven conventions, uses Spring Boot DevTools for hot reloading, and enforces the typical Java code style.

---

## Contributing

Pull requests are welcome.  
For large changes, create an issue first.  
Make sure all tests pass and code style checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Changelog

- **2026‑09‑07** – Updated README, badges, and added Docker support.  
- **2026‑09‑03** – Added Docker support and badges.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
