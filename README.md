# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that lets coworkers quickly schedule a 15‑minute spontaneous coffee chat.  
Users join a matchmaking queue, add interest tags, chat in real time, and start a peer‑to‑peer WebRTC video call.  
An admin portal provides visibility into user activity and aggregated analytics.

---

## Features

| Feature | Description |
|---------|-------------|
| Random / interest‑based matching | 15‑minute sessions between matched colleagues |
| Interest tags | Add topics to improve match relevance |
| Real‑time chat | WebSocket/STOMP with text, images, and voice notes |
| WebRTC video call | Low‑latency peer‑to‑peer signaling via WebSocket |
| Admin dashboard | User management, activity monitoring, aggregated analytics |
| Open source | MIT licensed, ready for extension |

---

## Tech stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.4 |
| Database | PostgreSQL |
| Frontend | Thymeleaf + Bootstrap 5 |
| WebSocket | STOMP over SockJS |
| Build | Maven |
| Runtime | JDK 21 + Maven 3.9+ |

---

## Quick start

### Prerequisites

- JDK 21
- Maven 3.9+

### Local development

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

The application will be reachable at <http://localhost:8080>.

### Docker (optional)

```bash
docker build -t coffeeconnect:latest .
docker run -d -p 8080:8080 --env-file .env coffeeconnect:latest
```

---

## Configuration

Configuration is read from environment variables or `application.yml`. The most common variables are:

| Variable | Description | Default |
|----------|-------------|---------|
| `DATASOURCE_URL` | JDBC URL for PostgreSQL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | Database user | `postgres` |
| `DATASOURCE_PASSWORD` | Database password | `postgres` |
| `ADMIN_USERNAME` | Admin portal login | `admin` |
| `ADMIN_PASSWORD` | Admin portal password | `admin` |

All other settings can be overridden in `src/main/resources/application.yml`.

---

## How to use

1. **Log in** as a regular user or as the admin.  
2. Set your status to **“Open for Coffee”** – you are added to the matchmaking queue.  
3. Add at least three interest tags (e.g. *Java*, *Coffee Brewing*, *Design Patterns*).  
4. When a match is found, a notification appears in the chat window.  
5. Use the chat to send text, images, or voice notes. Click **Video Call** to initiate a WebRTC session.  

Admin users can access `/admin` to view user lists, active conversations, and aggregated metrics.

---

## Development

```bash
# Run unit & integration tests
mvn test
```

The project follows standard Maven conventions and uses Spring Boot DevTools for hot reloading.

---

## Contributing

Pull requests are welcome. Please open an issue for major changes and run tests and linting before submitting.

---

## License

MIT © 2026, shubhyagami

---

## Badges

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)
![License-MIT](https://img.shields.io/badge/License-MIT-purple.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)

---

## Changelog

- **2026‑09‑03** – Cleaned up README, added Docker support and additional badges.  
- **2026‑09‑02** – Minor README cleanup.  
- **2026‑08‑21** – Refined structure and clarified environment configuration.  
- **2026‑08‑10** – Fixed WebSocket timing issue that delayed match notifications.  
- **2026‑07‑15** – Added interest‑tag support and voice‑note functionality.
