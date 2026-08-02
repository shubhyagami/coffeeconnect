# CoffeeConnect

```text
      ((((
      ((((      ____                 _                     ___                   _   
      ((((     / ___| ___   ___   __| |  ___  __ _ _ __ __| / __| ___   ___ _ __ | |_ 
      ((((    | |    / _ \ / _ \ / _` | / __|/ _` | '__/ _` \__ \/ -_) / _ \ '  \|  _|
      ((((    | |___ | (_) | (_) | (_| | \__ \ (_| | | | (_| |___/\__ \| (_) | |_) | | 
      ((((     \____|\___/ \___/ \__,_| |___/\__,_|_|  \__,_|   |___(_)___/| .__/ \__|
    (((((((((((((                                                        |_|     
```

![Java 21](https://img.shields.io/badge/Java-21-blue) ![Spring Boot 3.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green) ![Thymeleaf](https://img.shields.io/badge/Templates-Thymeleaf%20%2B%20Bootstrap%205-orange) ![WebRTC](https://img.shields.io/badge/Video-WebRTC-lightblue) ![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue) ![Served Hot](https://img.shields.io/badge/Coffee-Served%20Hot-brown)

**Employee Coffee Connection Platform** — a Spring Boot application that helps colleagues discover, connect, and chat over coffee. It features video calls, real-time messaging, voice recording, media sharing, and a full admin portal.

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

## 📜 Weekly Highlight - The "Watercooler" Evolution

In traditional office spaces, the watercooler or the coffee machine was the nexus of cross-team collaboration. **CoffeeConnect** digitizes this exact experience for the hybrid-work era. 

Instead of forcing awkward standalone meeting links, CoffeeConnect recreates the organic "walk-up-to-the-machine" vibe:
1. **State Transparency:** See who is currently on a "Coffee Break" in real-time via WebSocket status feeds.
2. **Spontaneous Pairing:** Join an open WebRTC video booth with a colleague who shares your exact coffee preferences (e.g., *Oat Milk Flat White* drinkers unite!).
3. **Zero Commitment:** No calendar invites, no 30-minute mandatory blocks—just a 5-minute mental reset to boost psychological safety across organizational silos.

---

## 💡 Pro Tips for Baristas (Developers & Admins)

- **Hot Reload Magic:** If you are running the app locally via `mvn spring-boot:run`, ensure you have `spring-boot-devtools` active in your `pom.xml` for instant Thymeleaf template changes without restarting the server.
- **Debugging WebRTC Signaling:** If video calls fail to establish PeerConnections, check your local browser's network tab for the raw WebSocket upgrade response. Ensure your `VideoCallWebSocketConfig` isn't being overridden by Spring Security's default CSRF protection on WebSockets.
- **H2 Console Access:** During local development, you can access the H2 in-memory database console at `/h2-console` using the JDBC URL `jdbc:h2:mem:coffeeconnect` to quickly inspect users and chat messages.

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
- [ ] **Reactions & Brewing Status** – Custom emojis and presence indicators tailored for micro-breaks

---

## ☕ Changelog

### 2026-08-03
- **Docs:** Refreshed Sacred Timeline alignment protocols in README.
- **Docs:** Added the *Weekly Highlight* section to redefine remote social interactions.
- **Docs:** Added *Pro Tips* for rapid onboarding and WebRTC debugging.

---

> *"A cup of coffee shared with a friend is happiness tasted and time well spent."*
> Drink deeply from the cup of collaboration, for the codebase that breaks bread (or beans) together, stays together.