# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot app that lets coworkers pair for a quick 15‑minute coffee chat.  
It offers interest‑based matching, real‑time text, media and voice chat, and a peer‑to‑peer WebRTC video call.  
An optional admin dashboard shows basic user metrics.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
  - [Local Development](#local-development)
  - [Docker](#docker)
- [Configuration](#configuration)
- [Usage](#usage)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)
- [Badges](#badges)
- [Changelog](#changelog)

---

## Overview

- **Matchmaking** – an algorithm queues users marked as *Open for Coffee* and pairs them within 15 minutes.
- **Interest Tags** – add topics (e.g., Java, Coffee Brewing) to improve match relevance.
- **Real‑time Chat** – WebSocket/STOMP delivers messages, images, and voice notes instantly.
- **WebRTC Video** – a low‑latency peer‑to‑peer call using WebSocket signaling.
- **Admin Dashboard** – `/admin` shows users, active conversations, and aggregated metrics.
- **Open‑Source** – MIT licensed.

---

## Tech Stack

| Component | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.4.4 |
| PostgreSQL | |
| Thymeleaf / Bootstrap 5 | |
| WebSocket (STOMP over SockJS) | |
| Maven | 3.9+ |
| Docker | optional |

---

## Quick Start

### Prerequisites
- JDK 21
- PostgreSQL (or any JDBC‑compatible database)
- Maven 3.9+ (or the provided Docker image)
- Docker (if you prefer containers)

### Local Development

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

Open <http://localhost:8080>.

### Docker

```bash
docker build -t coffeeconnect:latest .
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

Create a **.env** file with the variables shown in *Configuration*.

---

## Configuration

CoffeeConnect reads settings from environment variables or `src/main/resources/application.yml`.  
The following variables have defaults; override them in `.env` or `application.yml`.

| Variable | Description | Default |
|----------|-------------|--------|
| `DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB user | `postgres` |
| `DATASOURCE_PASSWORD` | DB password | `postgres` |
| `ADMIN_USERNAME` | Admin login | `admin` |
| `ADMIN_PASSWORD` | Admin password | `admin` |

Example `.env`:

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

---

## Usage

1. Log in as a user or the admin.  
2. Set status to **“Open for Coffee”** to enter the matchmaking queue.  
3. Add at least three interest tags.  
4. When a match is found, a notification appears.  
5. Use the chat to send text, images, or voice notes.  
6. Click **Video Call** to start a WebRTC session.  
7. Admins can visit `/admin` for user lists, active sessions, and metrics.

---

## Development

```bash
mvn test
mvn fmt:format
```

The project follows Maven conventions and uses Spring Boot DevTools for hot reloading.

---

## Contributing

Pull requests are welcome.  
For large changes, open an issue first.  
Ensure all tests pass and code style checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Badges

![Build Status](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?branch=main&label=build&logo=github)
![Java 21](https://img.shields.io/badge/Java-21-blue)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)
![License MIT](https://img.shields.io/badge/License-MIT-purple)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)

---

## Changelog

- **2026‑09‑06**: README tidy‑up, updated badges.  
- **2026‑09‑03**: Added Docker support and badges.  
- **2026‑08‑10**: Fixed WebSocket timing issue.  
- **2026‑07‑15**: Added interest‑tag and voice‑note support.
