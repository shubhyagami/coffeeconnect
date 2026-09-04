# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that lets coworkers quickly schedule 15‑minute spontaneous coffee chats.  
Users join a matchmaking queue, attach interest tags, chat in real time, and then start a peer‑to‑peer WebRTC video call.  
An optional admin portal provides visibility into user activity and aggregated analytics.

---

## Features

- **Random & Interest‑Based Matching** – 15‑minute sessions between matched colleagues  
- **Interest Tags** – Add topics to improve match relevance  
- **Real‑time Chat** – Text, images, and voice notes via WebSocket/STOMP  
- **WebRTC Video Call** – Low‑latency peer‑to‑peer call with signaling over WebSocket  
- **Admin Dashboard** – User management, activity monitoring, aggregated analytics  
- **Open Source** – MIT licensed

---

## Tech Stack

- Java 21  
- Spring Boot 3.4.4  
- PostgreSQL  
- Thymeleaf + Bootstrap 5  
- WebSocket (STOMP over SockJS)  
- Maven  
- Docker (optional)

---

## Getting Started

### Prerequisites

- JDK 21  
- PostgreSQL (or another JDBC‑compatible database)  
- Maven 3.9+  
- Docker (if you prefer containerised setup)

### Local Setup

```text
# Clone the repository
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect

# Compile and run
mvn spring-boot:run
```

Open <http://localhost:8080> in your browser.

### Docker Setup

```text
# Build the image
docker build -t coffeeconnect:latest .

# Run the container
docker run -d -p 8080:8080 --env-file .env coffeeconnect:latest
```

Create a `.env` file in the project root with the needed variables (see **Configuration**).

---

## Configuration

CoffeeConnect reads configuration from environment variables or `src/main/resources/application.yml`.  
Typical environment variables are:

| Variable           | Description                                      | Default |
|--------------------|--------------------------------------------------|---------|
| `DATASOURCE_URL`   | JDBC URL for PostgreSQL                          | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | Database user                                 | `postgres` |
| `DATASOURCE_PASSWORD` | Database password                               | `postgres` |
| `ADMIN_USERNAME`   | Admin portal login                               | `admin` |
| `ADMIN_PASSWORD`   | Admin portal password                            | `admin` |

All other settings can be overridden in `application.yml`.

---

## Using CoffeeConnect

1. **Log in** – as a regular user or as the admin.  
2. Set your status to **“Open for Coffee”** to enter the matchmaking queue.  
3. Add at least three interest tags (e.g. *Java*, *Coffee Brewing*, *Design Patterns*).  
4. When a match is found, a notification appears in the chat window.  
5. Use the chat to send text, images, or voice notes.  
   Click **Video Call** to start a WebRTC session.

Admins can visit `/admin` to view user lists, active conversations, and aggregated metrics.

---

## Development

```text
# Run unit tests
mvn test

# Format the code (if you prefer)
mvn fmt:format
```

The project follows standard Maven conventions and uses Spring Boot DevTools for hot reloading.

---

## Contributing

Pull requests are welcome. For large changes, open an issue first.  
Make sure tests pass and style checks succeed before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Badges

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)
![Build Maven](https://img.shields.io/badge/Build-Maven-33A9FF.svg)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)
![License MIT](https://img.shields.io/badge/License-MIT-purple.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)

---

## Changelog

- **2026‑09‑03** – Cleaned up README, added Docker support and badges.  
- **2026‑09‑02** – Minor README cleanup.  
- **2026‑08‑21** – Refined structure and clarified environment configuration.  
- **2026‑08‑10** – Fixed WebSocket timing issue that delayed match notifications.  
- **2026‑07‑15** – Added interest‑tag support and voice‑note functionality.
