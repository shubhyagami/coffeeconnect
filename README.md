[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
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
- [Features](#features)
- [Tech stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
  - [Run locally with the Maven wrapper](#run-locally-with-the-maven-wrapper)
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

CoffeeConnect uses a modern Java stack to provide fast, low-latency communication for small teams. It is designed to make casual coworker interactions feel spontaneous without adding another heavy collaboration tool.

---

## Features

- **Automatic pairing** – users marked as “Open for Coffee” are matched with another available colleague.
- **Rich messaging** – chat, image sharing, and voice notes.
- **Video calls** – peer-to-peer WebRTC sessions.
- **Admin dashboard** – session metrics, active user list, and system health at `/admin`.
- **Hot reload** – rapid development with Spring DevTools.

---

## Tech stack

- **Backend** – Java 21, Spring Boot 3.4.4
- **Persistence** – PostgreSQL for production, H2 for local development
- **Realtime** – WebSocket/STOMP and WebRTC
- **Build** – Maven

---

## Prerequisites

- JDK 21
- Maven 3.9+ (or use the included Maven wrapper)
- Docker and Docker Compose (optional)
- PostgreSQL (optional, for a production-like local setup)

---

## Getting started

### Run locally with the Maven wrapper

    git clone https://github.com/shubhyagami/coffeeconnect.git
    cd coffeeconnect
    ./mvnw spring-boot:run

Open `http://localhost:8080` in a browser.

### Run with Docker Compose

    docker compose up -d

The app is available at `http://localhost:8080`. The Compose file starts a PostgreSQL container and the application.

If you are using an older Docker installation, use `docker-compose up -d` instead.

---

## Configuration

Edit `src/main/resources/application.yml` or `application.properties`. Common properties:

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/coffeeconnect` | JDBC URL |
| `spring.datasource.username` | `postgres` | Database user |
| `spring.datasource.password` | `postgres` | Database password |
| `server.port` | `8080` | HTTP port |
| `spring.websocket.stomp.endpoint` | `/ws` | WebSocket endpoint |
| `coffeeconnect.admin.enabled` | `true` | Enable the admin UI |

---

## Using the app

1. Register or sign in.
2. Set your status to **Open for Coffee** in the profile panel.
3. Wait for the system to pair you with another open user.
4. You will be redirected to a private room where you can chat, share media, or start a video call.

---

## Development

### Running tests

    ./mvnw test

The test suite covers REST endpoints, WebSocket interactions, and pairing logic.

### Formatting code

    ./mvnw spotless:apply

This applies the project’s code style configuration.

---

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/awesome`).
3. Commit your changes (`git commit -m "Add awesome feature"`).
4. Push the branch (`git push origin feature/awesome`).
5. Open a pull request.

Please keep tests updated and follow the existing coding style.

---

## License

MIT – see the [LICENSE](LICENSE) file.

---

## Changelog

### v1.0.0 – 2026-09-29

- Initial stable release.
- WebRTC video integration.
- Admin dashboard with session metrics.
- Updated to Spring Boot 3.4.4 and Java 21.
