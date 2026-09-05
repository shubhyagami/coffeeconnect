# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that lets coworkers instantly pair for a 15‑minute coffee chat.  
Features include interest‑based matchmaking, real‑time text and media chat, and a peer‑to‑peer WebRTC video call.  
An optional admin dashboard shows basic user metrics and analytics.

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Local Development](#local-development)
  - [Docker](#docker)
- [Configuration](#configuration)
- [How to Use](#how-to-use)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)
- [Badges](#badges)
- [Changelog](#changelog)

---

## Features

- **Instant & Interest‑Based Matching** – 15‑minute sessions between matched colleagues.  
- **Interest Tags** – Add topics to improve match relevance.  
- **Real‑time Chat** – Send text, images, and voice notes via WebSocket/STOMP.  
- **WebRTC Video Call** – Low‑latency peer‑to‑peer call with WebSocket signaling.  
- **Admin Dashboard** – View users, active conversations, and aggregated analytics.  
- **Open‑Source** – MIT licensed.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.4**
- **PostgreSQL**
- **Thymeleaf + Bootstrap 5** (server‑side rendering)
- **WebSocket** (STOMP over SockJS)
- **Maven**
- **Docker** (optional)

---

## Getting Started

### Prerequisites

- JDK 21
- PostgreSQL (or any JDBC‑compatible database)
- Maven 3.9+ (or use the provided Docker image)
- Docker (if you prefer a containerised setup)

### Local Development

    git clone https://github.com/shubhyagami/coffeeconnect.git
    cd coffeeconnect
    mvn spring-boot:run

Open <http://localhost:8080> in your browser.

### Docker

Build the image and run it:

    docker build -t coffeeconnect:latest .
    docker run -d -p 8080:8080 --env-file .env coffeeconnect:latest

Create a **.env** file in the project root with the variables listed under *Configuration*.

---

## Configuration

CoffeeConnect reads settings from environment variables or `src/main/resources/application.yml`.  
The following environment variables have sensible defaults:

| Variable              | Description                                 | Default |
|-----------------------|---------------------------------------------|---------|
| `DATASOURCE_URL`      | JDBC URL for the database                   | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | Database user                              | `postgres` |
| `DATASOURCE_PASSWORD` | Database password                           | `postgres` |
| `ADMIN_USERNAME`      | Admin portal login                           | `admin` |
| `ADMIN_PASSWORD`      | Admin portal password                       | `admin` |

Override any setting in `application.yml` if you prefer.

---

## How to Use

1. **Log in** as a regular user or as the admin.  
2. Set your status to **“Open for Coffee”** to enter the matchmaking queue.  
3. Add at least three interest tags (e.g. *Java*, *Coffee Brewing*, *Design Patterns*).  
4. When a match is found, a notification appears in the chat window.  
5. Use the chat to send text, images, or voice notes. Click **Video Call** to start a WebRTC session.  
6. Admins can visit `/admin` to view user lists, active conversations, and aggregated metrics.

---

## Development

    mvn test
    mvn fmt:format

The project follows standard Maven conventions and uses Spring Boot DevTools for hot reloading.

---

## Contributing

Pull requests are welcome!  
For large changes, open an issue first.  
Make sure all tests pass and style checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Badges

![Build status](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?label=Build&logo=github)
![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)
![License MIT](https://img.shields.io/badge/License-MIT-purple.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)

---

## Changelog

- **2026‑09‑06** – Minor README tidy‑up, updated badges.  
- **2026‑09‑03** – Added Docker support and badges.  
- **2026‑09‑02** – Minor README cleanup.  
- **2026‑08‑21** – Refined structure and clarified environment configuration.  
- **2026‑08‑10** – Fixed WebSocket timing issue that delayed match notifications.  
- **2026‑07‑15** – Added interest‑tag support and voice‑note functionality.
