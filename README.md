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
git clone <repository-url>
cd coffeeconnect
mvn spring-boot:run
```

## Roadmap

The TVA Temporal Engineer catalog of planned variants — the SACRED TIMELINE.

- [ ] **Stage 1 — Latte Link Layer**
  - Pair random employees for spontaneous coffee matches
  - Auto-match by shared interests or team
  - Smart scheduler suggests the right time slots

- [ ] **Stage 2 — Espresso Event Horizon**
  - Post and discover local coffee events inside the org
  - Bean-of-the-week notifications
  - Calendar sync with company events

- [ ] **Stage 3 — Quantum Caffeine Protocol**
  - End-to-end encrypted calls (TVA-level security)
  - P2P audio fallback modes
  - Group huddles with breakout rooms

- [ ] **Stage 4 — The Barista Dashboard**
  - Coffee consumption analytics (anonymized)
  - Heat map of active chats
  - Export org-wide connection graphs

- [ ] **Stage 5 — Multiversal Federation**
  - SSO connectors (Google, GitHub, Okta)
  - Cross-org coffee invites
  - Public coffee shop directory

- [ ] **Stage 6 — PostgreSQL Pruning Engine**
  - Snapshot backups to cold storage
  - One-click restore points
  - Time-windowed data retention rules

- [ ] **Stage 7 — The Sacred Bean**
  - Automatically detect the true ruler of each timeline
  - Rewrite history so everyone gets their coffee on time
  - Reach 100% employee caffeine saturation

> *"For all time. Always."* — now also a coffee tagline.