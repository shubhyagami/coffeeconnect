# CoffeeConnect

A lightweight Spring Boot application that helps coworkers find a quick 15‑minute coffee chat.  
It matches users who are “Open for Coffee”, lets them exchange text, images, voice notes and start a WebRTC video call.  
An optional admin dashboard `/admin` displays basic metrics.

---

## Badges

[![Build Status](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?branch=main&label=build&logo=github)](https://github.com/shubhyagami/coffeeconnect/actions)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)](https://www.postgresql.org/)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-Compose-2496ED)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](CONTRIBUTING.md)

---

## Table of Contents

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

CoffeeConnect is built with **Spring Boot 3.4.4** and runs on **Java 21**.  
It uses a PostgreSQL database, Thymeleaf + Bootstrap 5 for the UI, and WebSocket/STOMP with SockJS for real‑time communication.  
All features are self‑contained: the video call is a peer‑to‑peer WebRTC session signaled through the same WebSocket channel.

---

## Features

| Feature | Description |
|---------|-------------|
| Matchmaking | Queues users marked “Open for Coffee” and pairs them within 15 minutes. |
| Interest Tags | Users can add tags (e.g., *Java*, *Coffee Brewing*) to improve matching relevance. |
| Text & Media Chat | Real‑time text, image, and voice‑note messaging via WebSocket/STOMP. |
| WebRTC Video | Low‑latency peer‑to‑peer video call with WebSocket signaling. |
| Admin Dashboard | `/admin` shows user lists, active sessions, and simple metrics. |
| Open‑Source | MIT licensed, ready for contribution. |

---

## Tech Stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.4 |
| PostgreSQL | 15+ |
| Thymeleaf | 3.2 |
| Bootstrap | 5.3 |
| WebSocket/STOMP (SockJS) | 2.7 |
| Maven | 3.9+ |
| Docker | optional |

---

## Getting Started

### Prerequisites

- JDK 21
- PostgreSQL (or any JDBC‑compatible database)
- Maven 3.9+ (or Docker, if you prefer containers)

### Local Development

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

Open <http://localhost:8080> in a browser.

> **Tip:** The application starts with a default in‑memory H2 database if no external DB is configured.  
> For production, provide a PostgreSQL connection (see *Configuration*).

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
|----------|-------------|---------|
| `DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB user | `postgres` |
| `DATASOURCE_PASSWORD` | DB password | `postgres` |
| `ADMIN_USERNAME` | Admin login | `admin` |
| `ADMIN_PASSWORD` | Admin password | `admin` |

Example `.env`:

```dotenv
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

`application.yml` can be used for additional Spring configuration.

---

## Usage

1. Log in as a regular user or the admin.  
2. Mark your status as **“Open for Coffee”** to join the matchmaking queue.  
3. Add at least three interest tags to improve matching.  
4. When a match is found, a notification appears.  
5. Use the chat panel to send text, images, or voice notes.  
6. Click **Video Call** to start a WebRTC session.  
7. Admins can visit `/admin` for user lists, active sessions, and metrics.

---

## Development

```bash
mvn test
mvn fmt:format
```

The project follows Maven conventions, uses Spring Boot DevTools for hot reloading, and follows the standard Java code style.

---

## Contributing

Pull requests are welcome.  
For large changes, open an issue first.  
Ensure all tests pass and code style checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Changelog

- **2026‑09‑07**: README cleaned up, badges updated.  
- **2026‑09‑03**: Added Docker support and badges.  
- **2026‑08‑10**: Fixed WebSocket timing issue.  
- **2026‑07‑15**: Added interest‑tag and voice‑note support.
