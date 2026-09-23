# CoffeeConnect

A lightweight Spring Boot application that pairs coworkers for a quick 15‑minute coffee chat.  
Users can mark themselves **Open for Coffee** and the system automatically matches them with another available colleague.  
Inside a match you can exchange text, images, voice notes, or start a low‑latency WebRTC video call.  
A simple admin dashboard (`/admin`) shows users, active sessions, and basic metrics.

---

## 📦 Build & Meta

| Badge |
|-------|
| ![Build](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/maven.yml?branch=main&label=build&logo=github) |
| ![Java](https://img.shields.io/badge/Java-21-blue) |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green) |
| ![Database](https://img.shields.io/badge/DB-PostgreSQL-blue) |
| ![Docker](https://img.shields.io/badge/Docker-Compose-2496ED) |
| ![License](https://img.shields.io/badge/License-MIT-purple) |
| ![PRs welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen) |

---

## 🚀 Quick start

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
```

### Run with Maven

```bash
mvn spring-boot:run
```

The application launches at `http://localhost:8080`.  If no datasource is configured, an in‑memory H2 database is used automatically.

### Run with Docker Compose

```bash
docker compose up -d
```

After a few seconds the UI is reachable at `http://localhost:8080`.

---

## ✨ Features

- **Matchmaking** – Automatic pairing of users marked *Open for Coffee* within 15 minutes.  
- **Interest tags** – Add tags (e.g. *Java*, *Coffee Brewing*) to refine matches.  
- **Real‑time chat** – Text, images, and voice notes via WebSocket/STOMP.  
- **WebRTC video** – Low‑latency peer‑to‑peer video call.  
- **Admin dashboard** – `/admin` page lists users, sessions, and metrics.  
- **Hot‑reload** – Spring Boot DevTools refreshes the app on code changes.

---

## 🛠️ Getting started

### Prerequisites

- JDK 21 (any modern distribution)
- Maven 3.9+ (to build/run with Maven) or Docker (for containerized deployment)
- (Optional) PostgreSQL or any JDBC‑compatible database

### Local development

```bash
mvn spring-boot:run
```

### Docker

```bash
docker build -t coffeeconnect:latest .
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

Create a `.env` file with the variables listed in the **Configuration** section.

---

## ⚙️ Configuration

CoffeeConnect reads its settings from environment variables or `src/main/resources/application.yml`.  
Variables that have defaults can be overridden.

| Variable            | Description                 | Default                              |
|---------------------|------------------------------|--------------------------------------|
| `DATASOURCE_URL`    | JDBC URL                     | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB user                | `postgres`                          |
| `DATASOURCE_PASSWORD` | DB password          | `postgres`                          |
| `ADMIN_USERNAME`    | Admin login                 | `admin`                             |
| `ADMIN_PASSWORD`    | Admin password              | `admin`                             |

**Example `.env`:**

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

These can also be configured directly in `application.yml`.

---

## 📖 Usage

1. Log in as any user or as the admin account.  
2. Click **Open for Coffee** and add at least three interest tags.  
3. When a match is found, a notification appears.  
4. Use the chat panel to send text, images, or voice notes.  
5. Click **Video Call** to start a WebRTC session.  
6. Admins can visit `/admin` to monitor users and sessions.

---

## 🧪 Development

```bash
# Run unit tests
mvn test

# Format code (uses fmt-maven-plugin)
mvn fmt:format
```

The project follows standard Maven conventions and enforces a basic Java code style.  

---

## 🤝 Contributing

Pull requests are welcome!  
For large changes, open an issue first.  
Ensure that all tests pass and formatting checks succeed before submitting your PR.

---

## 📄 License

MIT © 2026 shubhyagami

---

## 🗓️ Changelog

- **2026‑09‑23** – Minor README cleanup and updated Docker Compose instructions.  
- **2026‑09‑20** – README updated for clarity and migrations to Docker compose.  
- **2026‑09‑14** – Added quick‑start section and removed duplicate entries.  
- **2026‑09‑07** – Docker support and badges added.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
