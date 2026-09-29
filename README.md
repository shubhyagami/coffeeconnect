[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
[K[2m  [2mmodel openai/gpt-oss-20b failed, trying next...[0m[0m
[K[2m  [2mmodel openai/gpt-oss-120b failed, trying next...[0m[0m
# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application designed to foster spontaneous networking by pairing coworkers for short coffee chats. When a user marks themselves as **Open for Coffee**, the system automatically matches them with another available colleague and initializes a private session.

![Build status](https://github.com/shubhyagami/coffeeconnect/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/github/license/shubhyagami/coffeeconnect)
![Code coverage](https://img.shields.io/codecov/c/github/shubhyagami/coffeeconnect)
![Java 21](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)

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

CoffeeConnect leverages a modern Java stack to provide real-time connectivity with minimal overhead.

- **Backend:** Java 21 & Spring Boot 3.4.4
- **Persistence:** PostgreSQL (Production) / H2 (Development)
- **Communication:** WebSocket/STOMP for messaging, WebRTC for low-latency video calls
- **Management:** Integrated Admin Dashboard at `/admin`

## Features

- **Automatic Pairing:** Smart matching system for users currently "Open for Coffee."
- **Rich Communication:** Support for text messages, image sharing, and voice notes.
- **Live Video:** High-quality, peer-to-peer video calls via WebRTC.
- **Admin Insights:** Monitor active sessions, user growth, and system metrics through a centralized dashboard.
- **Developer Friendly:** Support for hot-reload to speed up the development cycle.

## Prerequisites

- JDK 21
- Maven 3.9+
- Docker & Docker Compose (optional, for containerized deployment)
- PostgreSQL (optional, for production-like local setup)

## Getting Started

### Local Maven Run
1. Clone the repository:
   ```bash
   git clone https://github.com/shubhyagami/coffeeconnect.git
   cd coffeeconnect
   ```
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access the app at `http://localhost:8080`.

### Docker Compose
For a fully configured environment including the database:
```bash
docker-compose up -d
```

## Configuration

The application uses `application.properties` (or `.yml`) for configuration. Key settings include:
- `spring.datasource.url`: Database connection string.
- `spring.websocket.stomp`: WebSocket configurations for real-time updates.
- `server.port`: Port on which the application runs (default: 8080).

## Using the App

1. **Join:** Register or log in to your profile.
2. **Match:** Toggle your status to **"Open for Coffee."**
3. **Connect:** Once matched, you will be redirected to a private session room.
4. **Chat:** Use the integrated chat tools or initiate a video call to meet your colleague.

## Testing & Formatting

### Running Tests
Execute the test suite using Maven:
```bash
./mvnw test
```

### Code Formatting
To ensure consistent style across the project:
```bash
./mvnw spotless:apply
```

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Changelog

### v1.0.0 (2026-09-29)
- Initial stable release.
- Implemented WebRTC video integration.
- Added Admin Dashboard with session metrics.
- Updated to Spring Boot 3.4.4 and Java 21.
