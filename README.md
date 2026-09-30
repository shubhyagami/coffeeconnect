[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# CoffeeConnect

**Foster spontaneous coffee chats at work.**

CoffeeConnect is a lightweight Spring Boot application that pairs coworkers for short coffee sessions. When a user marks themselves as **Open for Coffee**, the system automatically matches them with another available colleague and creates a private session for text, audio, or video communication.

![Build Status](https://github.com/shubhyagami/coffeeconnect/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/github/license/shubhyagami/coffeeconnect)
![Code Coverage](https://img.shields.io/codecov/c/github/shubhyagami/coffeeconnect)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)

---

## Table of contents

- [Overview](#overview)
- [Key features](#key-features)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
  - [Run locally with Maven](#run-locally-with-maven)
  - [Run with Docker Compose](#run-with-docker-compose)
- [Configuration](#configuration)
- [Using the app](#using-the-app)
- [Development](#development)
  - [Running tests](#running-tests)
  - [Formatting code](#formatting-code)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

---

## Overview

CoffeeConnect uses a modern Java stack to provide instant, low‑latency communication:

- **Backend** – Java 21, Spring Boot 3.4.4
- **Persistence** – PostgreSQL (production), H2 (dev)
- **Realtime** – WebSocket/STOMP, WebRTC
- **Admin** – Dashboard at `/admin` for monitoring active sessions and usage

---

## Key features

- **Automatic pairing** – users marked as “Open for Coffee” are immediately matched
- **Rich messaging** – chat, image, and voice note support
- **Video calls** – WebRTC peer‑to‑peer sessions
- **Admin dashboard** – session metrics, active user list, system health
- **Hot‑reload** – rapid development cycle with Spring DevTools

---

## Prerequisites

- JDK 21
- Maven 3.9+
- Docker & Docker Compose (optional)
- PostgreSQL (optional, for production‑like local setup)

---

## Getting started

### Run locally with Maven

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
./mvnw spring-boot:run
```

Open `http://localhost:8080` in a browser.

### Run with Docker Compose

```bash
docker-compose up -d
```

The app is accessible at `http://localhost:8080`. The compose file starts a PostgreSQL container and the application.

---

## Configuration

Edit `src/main/resources/application.yml` (or `application.properties`). Important properties:

| Property | Default | Description |
|----------|--------|------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/coffeeconnect` | JDBC URL |
| `spring.datasource.username` | `postgres` | DB user |
| `spring.datasource.password` | `postgres` | DB password |
| `server.port` | `8080` | HTTP port |
| `spring.websocket.stomp.endpoint` | `/ws` | WebSocket endpoint |
| `coffeeconnect.admin.enabled` | `true` | Enable admin UI |

---

## Using the app

1. **Login / register** – create or sign in.
2. **Toggle status** – set your presence to **Open for Coffee** in the profile panel.
3. **Match** – the system will pair you with another open user.
4. **Connect** – you’ll be redirected to a private room where you can chat, share media, or launch a video call.

---

## Development

### Running tests

```bash
./mvnw test
```

The suite covers REST endpoints, WebSocket interactions, and pairing logic.

### Formatting code

```bash
./mvnw spotless:apply
```

This applies the project's code style configuration.

---

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/awesome`).
3. Commit your changes (`git commit -m "Add awesome feature"`).
4. Push (`git push origin feature/awesome`).
5. Open a pull request.

Please keep tests updated and adhere to the existing coding style.

---

## License

MIT – see the [LICENSE](LICENSE) file.

---

## Changelog

### v1.0.0 – 2026‑09‑29

- Initial stable release
- WebRTC video integration
- Admin dashboard with session metrics
- Updated to Spring Boot 3.4.4 & Java 21
---
