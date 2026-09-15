# CoffeeConnect

A lightweight Spring Boot web app that matches coworkers for quick 15‑minute coffee chats. Users mark themselves **“Open for Coffee”** and the system pairs them with another available colleague. Within a match you can exchange text, images, voice notes, and start a low‑latency WebRTC video call. An optional admin dashboard (`/admin`) shows users, active sessions, and a handful of metrics.

---

## Status

[![Build](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?branch=main&label=build&logo=github)](https://github.com/shubhyagami/coffeeconnect/actions)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/DB-PostgreSQL-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)
[![PRs welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](CONTRIBUTING.md)

---

## Quick Start

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
# Run locally with Maven
mvn spring-boot:run

# Or with Docker Compose
docker compose up -d
```

Open <http://localhost:8080> to use the UI. If you don’t provide a database, an in‑memory H2 database is used automatically.

---

## Features

- **Matchmaking** – Automatic pairing of users marked “Open for Coffee” within 15 minutes.  
- **Interest Tags** – Add tags (e.g., *Java*, *Coffee Brewing*) to improve match quality.  
- **Real‑time Chat** – Text, images, and voice notes via WebSocket/STOMP.  
- **WebRTC Video Call** – Low‑latency peer‑to‑peer video, signaled over WebSocket.  
- **Admin Dashboard** – `/admin` lists users, sessions, and basic metrics.  
- **Hot‑Reloading** – Spring Boot DevTools for instant code changes.  

---

## Getting Started

### Prerequisites

- JDK 21 (OpenJDK or any modern distribution)  
- Maven 3.9+  
- (Optional) PostgreSQL or any JDBC‑compatible database  
- Docker (for containerized deployment)

### Local Development

```bash
mvn spring-boot:run
```

The app starts at <http://localhost:8080>.  
If no datasource is configured, an in‑memory H2 instance is used automatically.

### Docker

```bash
docker build -t coffeeconnect:latest .
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

Create a `.env` file with the variables listed in **Configuration**.

---

## Configuration

CoffeeConnect reads settings from environment variables or `src/main/resources/application.yml`.  
Variables with defaults can be overridden.

| Variable               | Description                     | Default |
|------------------------|---------------------------------|---------|
| `DATASOURCE_URL`       | JDBC URL                        | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME`  | DB user                         | `postgres` |
| `DATASOURCE_PASSWORD` | DB password                     | `postgres` |
| `ADMIN_USERNAME`       | Admin login                     | `admin` |
| `ADMIN_PASSWORD`       | Admin password                  | `admin` |

**Example `.env`:**

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

You can also set the same properties in `application.yml`.

---

## Usage

1. Log in (any user or the admin account).  
2. Mark your status as **“Open for Coffee”**.  
3. Add at least three interest tags.  
4. When a match is found, a notification appears.  
5. Use the chat panel to send text, images, or voice notes.  
6. Click **Video Call** to start a WebRTC session.  
7. Admins can visit `/admin` to monitor users and sessions.

---

## Development

```bash
# Run tests
mvn test

# Format code (uses fmt-maven-plugin)
mvn fmt:format
```

The project follows standard Maven conventions, uses Spring Boot DevTools for hot reloading, and enforces a basic Java code style.

---

## Contributing

Pull requests are welcome!  
For large changes, open an issue first.  
Please ensure all tests pass and formatting checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Changelog

- **2026‑09‑14** – README cleaned up, added quick‑start section, removed duplicate entries.  
- **2026‑09‑07** – Added Docker support and badges.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
