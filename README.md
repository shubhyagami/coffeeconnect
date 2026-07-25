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
├── enums/                             # 5 enums
├── repository/                        # 14 Spring Data JPA repositories
├── security/
│   ├── CustomUserDetails.java         # UserDetails wrapper
│   └── CustomUserDetailsService.java  # Loads user from DB
├── service/                           # 12 service classes
└── websocket/
    └── VideoCallWebSocketHandler.
```

---

## 🚀 Pro Tips

- **Coffee Roulette**: Use the *Discover* page to find a random colleague outside your team and send a coffee request. Perfect for cross‑department bonding.
- **Mute Notifications**: In the notification center, you can toggle silent mode during focus hours – coffee breaks can wait.
- **Video Call Shortcut**: Press `Ctrl+Shift+C` on the dashboard to instantly create a coffee‑break room (works after login).
- **Voice Notes**: Record a quick voice message instead of typing – great for walking coffee chats.

---

## 📝 Changelog

### [1.1.0] – 2026-07-25
- **Added** Video call quality indicator (good/fair/poor) based on network stats.
- **Enhanced** Admin portal with bulk user export (CSV/PDF).
- **Fixed** Stale WebSocket connections after page refresh.
- **Improved** Seed data: now includes 5 sample coffee request histories.

---

## ☕ Weekly Highlight

**This week’s featured feature**: *Smart Coffee Suggestions* – the app now suggests colleagues you haven't connected with in 30 days, based on past chat topics and common interests. Try it from the Dashboard sidebar!

---

> *"Good coffee and great conversation – the perfect recipe for a connected workplace."*  
> — CoffeeConnect Dev Team

---

*Built with ☕ and Java 21 by shubhyagami.*