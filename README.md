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
├── contr
```

---

## 🕰️ Roadmap – Approved by the Time Variance Authority

The Sacred Timeline of CoffeeConnect is ever-evolving. Below are the Nexus Events we plan to introduce – subject to pruning only if they create a branch that threatens the Multiverse of user experience.

### ✅ Currently on the Sacred Timeline (v1.0 – v1.2)
- [x] User registration & session-based authentication
- [x] Profile management with coffee preferences
- [x] Real‑time chat (STOMP + WebSocket)
- [x] WebRTC video calls (peer-to-peer, signaling via raw WebSocket)
- [x] Admin portal for user management
- [x] Voice recording & media sharing
- [x] H2 in‑memory DB for local dev, PostgreSQL for production

### 🚧 Under Temporal Observation (v1.3 – v1.5)
- [ ] **Coffee Blend Recommendations** – AI-powered suggestions based on chat history and mood tags
- [ ] **Group Video Rooms** – Multi‑participant WebRTC (Mesh/SFU architecture)
- [ ] **Event Calendar** – Schedule coffee meetups with push reminders (FCM)
- [ ] **Reactions & GIFs** – Inline message reactions with GIPHY integration
- [ ] **Dark Mode** – Because even the TVA needs a restful UI

### 🔮 Future Timeline Branches (v2.0+)
- [ ] **Mobile App** (Flutter / Kotlin Multiplatform) – Coffee on the go
- [ ] **Integration with Slack / Teams** – Cross‑platform coffee invitations
- [ ] **Gamification** – “Coffee Streaks”, badges, and leaderboards
- [ ] **Anonymous Feedback** – Rate your coffee chat experience (encrypted)
- [ ] **TVA‑style UI Overhaul** – Retro‑futuristic theme with CRT scanlines and timeline clocks

### ⚠️ Pruned Variants (Won’t Implement)
- ❌ **Cryptocurrency coffee payments** – Too volatile for the Sacred Timeline
- ❌ **Time‑travel debugging** – We leave that to the Minutemen

---

**Want to suggest a new feature?** File an issue on our repository – every variant timeline is welcome for review by the Time-Keepers (aka the maintainers).

*“For all time. Always.”*