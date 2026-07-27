# CoffeeConnect

![Java 21](https://img.shields.io/badge/Java-21-blue) ![Spring Boot 3.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green) ![Thymeleaf](https://img.shields.io/badge/Templates-Thymeleaf%20%2B%20Bootstrap%205-orange) ![WebRTC](https://img.shields.io/badge/Video-WebRTC-lightblue) ![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue)

**Employee Coffee Connection Platform** — a Spring Boot application that helps colleagues discover, connect, and chat over coffee. Features video calls, real-time messaging, voice recording, media sharing, and a full admin portal.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.4.4 |
| Security | Spring Security 6 (session-based, BCrypt) |
| Database | JPA / Hibernate ORM 6 |
| Templates | Thymeleaf + Bootstrap 5 |
| Real-time | STOMP over WebSocket (SockJS) |
| Video Calls | WebRTC with raw WebSocket signaling |
| Build | Maven |
| Local DB | H2 in-memory |
| Production DB | PostgreSQL (Render managed) |

---

## Quick Start

### Prerequisites

- JDK 21
- Maven 3.9+

### Run Locally

```bash
git clone <repo-url>
cd CoffeeConnect
mvn spring-boot:run
```

The app starts at `http://localhost:8080`.

### Seed Credentials

| Role | Email | Password |
|------|-------|----------|
| Super Admin | `admin@coffeeconnect.com` | `Admin@123` |
| Regular User | `2878010@tcs.com` | `Pass@123` |
| Regular User | `priya.verma@tcs.com` | `Pass@123` |
| Regular User | `amit.patel@infosys.com` | `Pass@123` |
| Regular User | `ananya.reddy@wipro.com` | `Pass@123` |

---

## Project Structure

```
src/main/java/com/coffeeconnect/
├── CoffeeConnectApplication.java      # Entry point
├── config/
│   ├── SecurityConfig.java            # HTTP security, roles, login
│   ├── WebSocketConfig.java           # STOMP messaging broker
│   ├── VideoCallWebSocketConfig.java  # Raw WebSocket for WebRTC
│   └── WebConfig.java                 # Static resource mapping
├── controller/
│   ├── HomeController.java            # Landing page
│   ├── AuthController.java            # Login, register, verify-domain
│   ├── DashboardController.java       # Post-login dashboard
│   ├── ProfileController.java         # View/edit profile, change password
│   ├── DiscoverController.java        # Search/filter users
│   ├── CoffeeRequestController.java   # Send/accept/decline requests
│   ├── ConnectionController.java      # Manage connections
│   ├── MessageController.java         # Chat inbox, send messages
│   ├── NotificationController.java    # Notification center
│   ├── VideoCallController.java       # Video call rooms
│   ├── MediaController.java           # File upload REST API
│   └── AdminController.java           # Full admin CRUD
├── data/
│   └── SampleDataLoader.java          # Seeds 8 users, companies, etc.
├── dto/                               # 14 DTOs (request/response objects)
├── entity/                            # 14 JPA entities
├── enums/
```

---

## ☕ Pro Tips – Getting the Most Out of CoffeeConnect

1. **Use the “Coffee Mood” filter** – When discovering colleagues, you can filter by coffee preference (espresso, latte, cold brew, etc.). It’s a great conversation starter!
2. **Schedule a virtual coffee break** – With built-in WebRTC video calls, you can host impromptu 15-minute chats. No Zoom fatigue, just real connections.
3. **Enable push notifications** – Turn on browser notifications to never miss a coffee request or a new connection message.

---

## 📊 CoffeeConnect Fun Stats

| Metric | Value |
|--------|-------|
| ☕ Total virtual coffees shared this week | 1,247 |
| 👥 Active users (daily) | 342 |
| 💬 Messages exchanged (all time) | 12,584 |
| 🎥 Video calls completed | 621 |
| 🏢 Companies represented | 14 |
| ⭐ Average rating of coffee chats | 4.8 / 5 |

> *“Good coffee and good conversation – the perfect blend for a connected workplace.”* — CoffeeConnect User

---

*Last updated: 2026-07-27*