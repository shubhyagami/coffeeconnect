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
![Status](https://img.shields.io/badge/Status-Active-success) ![License](https://img.shields.io/badge/License-MIT-purple) ![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)

**Employee Coffee Connection Platform** — a Spring Boot application that helps colleagues discover, connect, and chat over coffee. It features video calls, real-time messaging, voice recording, media sharing, and a full admin portal.

> *"Coffee is a language in itself."* — Jackie Chan

---

## Features

- **Spontaneous Matching:** Pair randomly with colleagues for a quick 15-minute coffee chat.
- **Interest Tags:** Improve match quality by adding shared interests like "Latte Art" or "Java Debugging."
- **Rich Messaging:** Real-time chat powered by WebSockets, including voice notes and media sharing.
- **Video Calls:** Peer-to-peer WebRTC video calls with low-latency signaling.
- **Admin Portal:** A full dashboard to manage users, monitor activity, and track anonymized connection metrics.

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

## Getting Started

### Prerequisites

- JDK 21
- Maven 3.9+

### Run Locally

```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```

The application will start on `http://localhost:8080`. It is configured to use an H2 in-memory database by default for local development, so no external database setup is required.

---

## User Tips

1. **Set your availability:** Update your status to "Open for Coffee" to appear in the matching queue. Colleagues can then book a chat directly from your profile.
2. **Use interest tags:** Add at least three tags to improve match quality. The algorithm favors shared interests over random pairing.
3. **Try voice notes:** Record a quick voice message from the chat window to add a personal touch.

---

## Roadmap

- [ ] **Local Coffee Events**
  - Post and discover local coffee events inside the org.
  - Bean-of-the-week notifications.
  - Calendar sync with company events.
- [ ] **Advanced Call Features**
  - End-to-end encrypted calls.
  - P2P audio fallback modes.
  - Group huddles with breakout rooms.
- [ ] **Connection Analytics**
  - Anonymized coffee consumption analytics.
  - Heat map of active chats.
  - Export org-wide connection graphs.
- [ ] **SSO & Federation**
  - SSO connectors (Google, GitHub, Okta).
  - Cross-org coffee invites.
  - Public coffee shop directory.

---

## Changelog

### August 7, 2026
- Resolved a minor timing anomaly in the WebSocket signaling layer that occasionally delayed coffee match notifications. Pairing notifications are now delivered instantly.
