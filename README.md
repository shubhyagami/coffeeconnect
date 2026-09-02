# CoffeeConnect

A lightweight Spring Boot app that enables spontaneous 15‑minute coffee chats between coworkers.  
Random, interest‑based matching, real‑time messaging, WebRTC video calls, and an admin portal for monitoring.

---

## Quick Start

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

Open <http://localhost:8080> to start using the app.

---

## Features

| Feature | Description |
|---------|--------------|
| **Spontaneous Matching** | 15‑minute random chat sessions start when both users are *Open for Coffee*. |
| **Interest Tags** | Add topics (e.g., “Latte Art”, “Java Debugging”) to refine match relevance. |
| **Rich Messaging** | WebSocket‑powered chat with voice notes and media uploads. |
| **WebRTC Video** | Peer‑to‑peer video calls with low‑latency signaling. |
| **Admin Dashboard** | User & activity management, anonymised analytics, and reporting. |

---

## Architecture & Technology

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.4
- **Database**: PostgreSQL
- **Frontend**: Thymeleaf + Bootstrap 5
- **Real‑time**: STOMP over SockJS (WebSocket)
- **Video**: WebRTC via raw WebSocket signalling
- **Build**: Maven

---

## Prerequisites

- JDK 21
- Maven 3.9+
- PostgreSQL (create a database named `coffeeconnect`)

`application.yml` (under `src/main/resources`) contains default config.  
If you prefer environment variables, set:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/coffeeconnect
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres
```

---

## Using the App

1. Log in / register.  
2. Set your status to **“Open for Coffee”** to be added to the matching queue.  
3. Add at least three interest tags.  
4. When matched, chat, send voice notes, or start a WebRTC video call.  
5. After the session ends, the status automatically reverts to *Away*.

---

## Roadmap

- Calendar integration & scheduled coffee events.  
- End‑to‑end encryption for calls.  
- P2P audio fallback & group huddles.  
- SSO with Google/GitHub/Okta.  
- Cross‑organization invites & public coffee‑shop directory.

---

## Contributing

Pull requests are welcome. For major changes, open an issue first.  
All contributions must pass the automated test suite (`mvn test`) and follow the code style guidelines in `pom.xml`.

---

## License

MIT © 2026 [shubhyagami](https://github.com/shubhyagami)

---

## Badges

![Build](https://img.shields.io/github/actions/workflow/status/shubhyagami/coffeeconnect/ci.yml?branch=main&label=Build&logo=github)
![Java 21](https://img.shields.io/badge/java-21-blue?logo=oracle)
![Spring Boot 3.4](https://img.shields.io/badge/spring%20boot-3.4-green?logo=spring)
![PostgreSQL](https://img.shields.io/badge/database-postgresql-blue?logo=postgresql)
![License MIT](https://img.shields.io/badge/license-MIT-purple)

---
