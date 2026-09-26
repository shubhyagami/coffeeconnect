[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that pairs coworkers for short, spontaneous coffee chats.  
When a user marks themselves **Open for Coffee**, the system automatically finds another available colleague and creates a private session. Inside the session you can exchange text, images, voice notes, or start a low‑latency WebRTC video call. The admin dashboard (`/admin`) shows users, active sessions, and key metrics.

![Build status](https://github.com/shubhyagami/coffeeconnect/actions/workflows/ci.yml/badge.svg)  
![License](https://img.shields.io/github/license/shubhyagami/coffeeconnect)  
![Code coverage](https://img.shields.io/codecov/c/github/shubhyagami/coffeeconnect?token=YOUR_TOKEN)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Local Maven Run](#local-maven-run)
  - [Docker Compose](#docker-compose)
- [Configuration](#configuration)
- [Using the App](#using-the-app)
- [Testing & Formatting](#testing--formatting)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

---

## Overview

- **Java 21 & Spring Boot 3.4.4**
- Persistence: PostgreSQL (or H2 for local development)
- Real‑time chat: WebSocket/STOMP, WebRTC
- Admin UI: `/admin`
- Hot‑reload: Spring Boot DevTools

---

## Features

| Feature | Description |
|---------|-------------|
| **Matchmaking** | Users marked *Open for Coffee* are paired automatically within 15 minutes. |
| **Interest Tags** | Filter peers by tags (`Java`, `Coffee Brewing`, etc.). |
| **Chat** | Text, images, and voice notes via WebSocket/STOMP. |
| **Video** | Low‑latency peer‑to‑peer WebRTC calls. |
| **Admin Dashboard** | View users, sessions, and simple metrics. |
| **Hot‑reload** | Development changes are applied instantly with DevTools. |

---

## Prerequisites

| Tool | Minimum version |
|------|-----------------|
| JDK   | 21 |
| Maven | 3.9+ |
| Docker | 20.10+ |
| PostgreSQL | 13+ (optional) |

---

## Getting Started

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
```

### Local Maven Run

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.  
If no datasource is configured, an in‑memory H2 database is used automatically.

### Docker Compose

```bash
docker compose up -d
```

Once the containers are up, the UI is reachable at `http://localhost:8080`.

---

## Configuration

CoffeeConnect reads from environment variables or `src/main/resources/application.yml`.  
The following variables are supported; all have sensible defaults for local development.

| Variable | Meaning | Default |
|----------|---------|--------|
| `DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | Database username | `postgres` |
| `DATASOURCE_PASSWORD` | Database password | `postgres` |
| `ADMIN_USERNAME` | Admin login | `admin` |
| `ADMIN_PASSWORD` | Admin password | `admin` |

Example `.env` file:

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

You can override these values directly in `application.yml` if you prefer configuration files over environment variables.

---

## Using the App

1. Log in as any user (or the admin account).  
2. Click **Open for Coffee** and add at least three interest tags.  
3. When a match is found, you’ll receive a notification.  
4. Use the chat panel to send text, images, or voice notes.  
5. Click **Video Call** to start a WebRTC session.  
6. Admins can visit `/admin` to monitor users and sessions.

---

## Testing & Formatting

```bash
# Run unit tests
mvn test

# Check code style
mvn fmt:check

# Auto‑format source code
mvn fmt:format
```

All PRs must pass both `mvn test` and `mvn fmt:format`.

---

## Contributing

We welcome contributions! For large changes, open an issue first to discuss. All pull requests must:

- Pass `mvn test` and `mvn fmt:format`.  
- Include tests for new functionality.  
- Follow the existing code style and conventions.

Please fork, create a feature branch, and submit a PR.

---

## License

MIT © 2026 shubhyagami

---

## Changelog

- **2026‑09‑26** – README cleaned up, added badges, improved guidance.  
- **2026‑09‑24** – Updated Docker Compose instructions.  
- **2026‑09‑23** – Reorganized features and quick‑start section.  
- **2026‑09‑20** – Simplified local dev guide and configuration.  
- **2026‑09‑14** – Added video call feature description.  
- **2026‑09‑07** – Added Docker support and badges.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
