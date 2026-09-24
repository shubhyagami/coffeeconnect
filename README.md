[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that automatically pairs coworkers for a quick 15‑minute coffee chat.  
Once a user marks themselves **Open for Coffee**, the system finds another available colleague and creates a private session.  
During a session you can send text, images, voice notes, or start a low‑latency WebRTC video call.  
An admin dashboard (`/admin`) shows users, active sessions, and key metrics.

---

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Quick Start](#quick-start)
4. [Prerequisites](#prerequisites)
5. [Local Development](#local-development)
6. [Docker](#docker)
7. [Configuration](#configuration)
8. [Usage](#usage)
9. [Testing & Formatting](#testing-and-formatting)
10. [Contributing](#contributing)
11. [License](#license)
12. [Changelog](#changelog)

---

## 🔧 Overview

- Written in **Java 21** and **Spring Boot 3.4.4**.
- Uses **PostgreSQL** for persistence (or an auto‑configured in‑memory H2 in development).
- Real‑time features powered by **WebSocket/STOMP** and **WebRTC**.
- Deployable via Docker Compose or as a standard Spring Boot jar.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| **Matchmaking** | Users marked *Open for Coffee* are automatically paired within 15 minutes. |
| **Interest Tags** | Add tags (e.g., *Java*, *Coffee Brewing*) to find like‑minded colleagues. |
| **Real‑time chat** | Text, images, and voice notes over WebSocket/STOMP. |
| **WebRTC video** | Peer‑to‑peer video call with low latency. |
| **Admin dashboard** | `/admin` page lists users, active sessions, and simple metrics. |
| **Hot‑reload** | Spring Boot DevTools refreshes the app on code changes. |

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

The app starts at `http://localhost:8080`.  
If no datasource is configured, an in‑memory H2 database is automatically used.

### Run with Docker Compose

```bash
docker compose up -d
```

After a few seconds the UI is reachable at `http://localhost:8080`.

---

## 📦 Prerequisites

| Tool | Minimum version |
|------|-----------------|
| JDK  | 21              |
| Maven | 3.9+           |
| Docker | any recent version |
| PostgreSQL (optional) | any JDBC‑compatible database |

---

## 🛠️ Local development

```bash
# Start the application
mvn spring-boot:run
```

You can also build a fat jar and run it:

```bash
mvn clean package
java -jar target/coffeeconnect-*.jar
```

---

## 🐳 Docker

```bash
# Build the image
docker build -t coffeeconnect:latest .

# Run the container
docker run -d -p 8080:8080 \
  --env-file .env \
  coffeeconnect:latest
```

> **Tip:** The provided `docker-compose.yml` pulls a PostgreSQL image and sets up the database automatically.  
> Start with `docker compose up -d` to launch both services.

---

## ⚙️ Configuration

CoffeeConnect reads configuration from environment variables or `src/main/resources/application.yml`.  
Variables below have sensible defaults.

| Variable | Meaning | Default |
|---------|---------|--------|
| `DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/coffeeconnect` |
| `DATASOURCE_USERNAME` | DB username | `postgres` |
| `DATASOURCE_PASSWORD` | DB password | `postgres` |
| `ADMIN_USERNAME` | Admin login | `admin` |
| `ADMIN_PASSWORD` | Admin password | `admin` |

**Example `.env`**

```
DATASOURCE_URL=jdbc:postgresql://db:5432/coffeeconnect
DATASOURCE_USERNAME=coffee_user
DATASOURCE_PASSWORD=secret
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret
```

You can also set these values directly in `application.yml`.

---

## 📖 Usage

1. Log in as any user (or the admin account).
2. Click **Open for Coffee** and add at least three interest tags.
3. When a match is found, a notification appears.
4. Use the chat panel to send text, images, or voice notes.
5. Click **Video Call** to start a WebRTC session.
6. Admins can visit `/admin` to monitor users and sessions.

---

## 🧪 Testing & formatting

```bash
# Run unit tests
mvn test

# Format source code
mvn fmt:format
```

The project also includes a simple code‑style check that must pass before merging.

---

## 🤝 Contributing

We welcome contributions!  
For large changes, open an issue first to discuss the approach.  
All PRs should:

- Pass `mvn test`.
- Pass `mvn fmt:format`.
- Include any new tests for new features.

Please follow the existing code style and conventions.

---

## 📄 License

MIT © 2026 shubhyagami

---

## 🗓️ Changelog

- **2026‑09‑24** – README cleanup and updated Docker Compose instructions.  
- **2026‑09‑23** – Added quick‑start section, reorganized features.  
- **2026‑09‑20** – Updated configuration section, simplified local dev guide.  
- **2026‑09‑14** – Added video call feature description.  
- **2026‑09‑07** – Added Docker support and badges.  
- **2026‑08‑10** – Fixed WebSocket timing issue.  
- **2026‑07‑15** – Added interest‑tag and voice‑note support.
