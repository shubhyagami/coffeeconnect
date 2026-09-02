# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that connects coworkers for 15‑minute spontaneous coffee chats. Users can join a queue, add interest tags, chat in real time (text, voice, images), and start a peer‑to‑peer WebRTC call. An admin portal gives visibility into user activity and aggregated analytics.

## Features

- **Random / Interest‑based matching** – 15‑minute sessions between matched colleagues.  
- **Interest tags** – add topics to improve relevance.  
- **Real‑time chat** – WebSocket / STOMP with media support (images, voice notes).  
- **WebRTC video call** – low‑latency peer‑to‑peer signaling via WebSocket.  
- **Admin dashboard** – user management, activity monitoring, aggregated analytics.  
- **Open source** – MIT licensed, ready for extension.

## Tech stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.4 |
| Database | PostgreSQL |
| Frontend | Thymeleaf + Bootstrap 5 |
| WebSocket | STOMP over SockJS |
| Build | Maven |
| Runtime | JDK 21, Maven 3.9+ |

## Getting started

### Prerequisites

- JDK 21  
- Maven 3.9+

### Install & run

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

The application will be available at <http://localhost:8080>.

### Configuration

The application reads the following environment variables (or `application.yml` properties):

| Variable | Description | Default |
|-----------|--------------|---------|
| `DATASOURCE_URL` | JDBC URL for PostgreSQL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB user | `postgres` |
| `DATASOURCE_PASSWORD` | DB password | `postgres` |
| `ADMIN_USERNAME` | Admin portal login | `admin` |
| `ADMIN_PASSWORD` | Admin portal password | `admin` |

All other settings can be overridden in `src/main/resources/application.yml`.

## Using the application

1. Log in as a user.  
2. Set your status to **“Open for Coffee”** → you enter the matchmaking queue.  
3. Add at least three interest tags (e.g. *“Java”, “Coffee Brewing”, “Design Patterns”*).  
4. When a match is found, you will be notified via the chat window.  
5. Use the chat to send text, images or voice notes, and click **Video Call** to initiate a WebRTC session.

Admins can access `/admin` to view user lists, active conversations and aggregate metrics.

## Development

```bash
# Run unit & integration tests
mvn test
```

The project follows standard Maven conventions and uses Spring Boot DevTools for hot reloading.

## Roadmap

- Calendar integration for scheduled coffee events.  
- End‑to‑end encryption for calls and fallback audio.  
- Group huddles and multi‑party video.  
- SSO connectors (Google, GitHub, Okta).  
- Public coffee‑shop directory.

## Contributing

Pull requests are welcome. For major changes, open an issue first. Please run tests and lint before submitting.

## License

MIT © 2026, shubhyagami

## Badges

[![Java 21](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)  
[![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)](https://www.postgresql.org)  
[![License-MIT](https://img.shields.io/badge/License-MIT-purple.svg)](https://opensource.org/licenses/MIT)  
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/shubhyagami/coffeeconnect/pulls)

## Changelog

- **2026‑09‑02** – Minor README cleanup.  
- **2026‑08‑21** – Refined README structure and clarified environment configuration.  
- **2026‑08‑10** – Fixed WebSocket timing issue that delayed match notifications.  
- **2026‑07‑15** – Added interest‑tag support and voice‑note functionality.
