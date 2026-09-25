[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that pairs coworkers for a quick 15‑minute coffee chat.  
When a user marks themselves **Open for Coffee**, the system automatically finds another available colleague and creates a private session. Within a session you can send text, images, voice notes, or start a low‑latency WebRTC video call. An admin dashboard (`/admin`) shows users, active sessions, and key metrics.

![Build status](https://github.com/shubhyagami/coffeeconnect/actions/workflows/ci.yml/badge.svg) ![License](https://img.shields.io/github/license/shubhyagami/coffeeconnect)

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Getting Started](#getting-started)
4. [Prerequisites](#prerequisites)
5. [Local Development](#local-development)
6. [Docker](#docker)
7. [Configuration](#configuration)
8. [Usage](#usage)
9. [Testing & Formatting](#testing--formatting)
10. [Contributing](#contributing)
11. [License](#license)
12. [Changelog](#changelog)

---

## Overview

- **Java 21** & **Spring Boot 3.4.4**
- Persistence: PostgreSQL (or auto‑configured H2 in development)
- Real‑time: WebSocket/STOMP, WebRTC
- Deployable via Docker Compose or as a standard Spring Boot jar

---

## Features

- **Matchmaking** – automatically pair users marked *Open for Coffee* within 15 minutes
- **Interest Tags** – filter peers by tags such as `Java`, `Coffee Brewing`
- **Real‑time chat** – send text, images, and voice notes via WebSocket/STOMP
- **WebRTC video** – low‑latency peer‑to‑peer video calls
- **Admin dashboard** – `/admin` page lists users, active sessions, and simple metrics
- **Hot‑reload** – Spring Boot DevTools updates the app on code changes

---

## Getting Started

Clone the repository and launch the application.

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
```

### Run locally with Maven

```bash
mvn spring-boot:run
```

The default URL is `http://localhost:8080`.  
If no datasource is configured, an in‑memory H2 database is used automatically.

### Run with Docker Compose

```bash
docker compose up -d
```

After a few seconds the UI is reachable at `http://localhost:8080`.

---

## Prerequisites

| Tool | Minimum version |
|------|-----------------|
| JDK   | 21 |
| Maven | 3.9+ |
| Docker | Any recent version |
| PostgreSQL | Any JDBC‑compatible database (optional) |

---

## Local Development

```bash
# Start the application
mvn spring-boot:run
```

To build an executable jar:

```bash
mvn clean package
java -jar target/coffeeconnect-*.jar
```

The project is configured for IntelliJ, VS Code, or any Java IDE.

---

## Docker

```bash
# Build the image
docker build -t coffeeconnect:latest .

# Run the container
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

The provided `docker-compose.yml` pulls a PostgreSQL image and sets up the database automatically.  
Run both services together with `docker compose up -d`.

---

## Configuration

CoffeeConnect reads environment variables or `src/main/resources/application.yml`.  
Defaults are provided for local development.

| Environment variable | Meaning | Default |
|-----------------------|---------|---------|
| `DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB username | `postgres` |
| `DATASOURCE_PASSWORD` | DB password | `postgres` |
| `ADMIN_USERNAME` | Admin login | `admin` |
| `ADMIN_PASSWORD` | Admin password | `admin` |

**Example `.env`**

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

You may also override these values directly in `application.yml`.

---

## Usage

1. Log in as any user (or the admin account).
2. Click **Open for Coffee** and add at least three interest tags.
3. When a match is found, a notification appears.
4. Use the chat panel to send text, images, or voice notes.
5. Click **Video Call** to start a WebRTC session.
6. Admins can visit `/admin` to monitor users and sessions.

---

## Testing & Formatting

```bash
# Run unit tests
mvn test

# Format source code
mvn fmt:format
```

The project includes a simple code‑style check that must pass before merging.

---

## Contributing

We welcome contributions! For large changes, open an issue first to discuss. All PRs should:

- Pass `mvn test`.
- Pass `mvn fmt:format`.
- Include tests for new features.

Please follow the existing code style and conventions.

---

## License

MIT © 2026 shubhyagami

---

## Changelog

- **2026‑09‑24** – README cleanup and updated Docker Compose instructions.  
- **2026‑09‑23** – Added quick‑start section, reorganized features.  
- **2026‑09‑20** – Updated configuration section, simplified local dev guide.  
- **2026‑09‑14** – Added video call feature description.  
- **2026‑09‑07** – Added Docker support and badges.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
