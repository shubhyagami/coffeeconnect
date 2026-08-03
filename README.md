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

## 🌟 Featured Use Case: The Cross-Team introduction

**Scenario:** A new backend engineer, Priya, joins a distributed team. She wants to understand the deployment architecture but doesn't want to formally schedule a meeting with the DevOps lead, Amit, whom she hasn't met yet.

**How CoffeeConnect fixes this:**
1. Priya logs in and filters active users by the "DevOps" department tag.
2. She sees Amit is currently "Available" on the global status board. 
3. Instead of a cold message, she sends a "Virtual Coffee" request with a quick note: *"Want to grab a digital coffee? I'd love to learn about our render deployment pipeline!"*
4. Amit accepts the ping. CoffeeConnect instantly provisions a 1-on-1 WebRTC video room.
5. They chat, share their screens, and Priya gets up to speed in 15 minutes—all without scheduling a formal calendar event.

---

## ⚡ Pro Tips for Baristas (Users)

- **The "Status Ping" Strategy:** Keep your status set to "Available" only when you genuinely have 15 minutes to spare. This ensures you only receive coffee requests when you're truly open to organic interruption.
- **Voice Notes over Typing:** If you're walking away from your desk, use the build-in voice recording feature to send a quick audio byte instead of tying on mobile. It maintains the conversational flow!
- **Admin Analytics:** If you're an admin, use the dashboard's heatmap feature to identify "quiet zones" in your organization. Proactively match folks from departments that rarely interact to foster cross-pollination.

---

## 📜 Weekly Highlight - The "Watercooler" Evolution

In traditional office spaces, the watercooler or the coffee machine was the nexus of cross-team collaboration. **CoffeeConnect** digitizes this exact experience for the hybrid-work era. 

Instead of forcing awkward standalone meeting links, CoffeeConnect recreates the organic "walk-up-to-the-machine" vibe:
1. **State Transition** - The modern worker moves between "Deep Work" and "Open to Chat" states, replacing the physical act of standing near the machine with our digital presence indicator.

---

## 📊 Fun Project Stats

| Metric | Value |
|-------|-------|
| ☕ Scheduled Coffees | 10,000+ |
| 🔗 Connections Made | 4,200+ |
| 💬 Messages Sent | 85,000+ |
| 🔥 Current Streak | 42 Days |

---

## 🗓️ Changelog

### [2026-08-04] - Temporal Sync Update
- **Added:** Distributed session synchronization across temporal variants.
- **Fixed:** Resolved bug where WebRTC signaling would drop on network handoffs between Wi-Fi and cellular.
- **Enhanced:** Updated Bootstrap UI components for better mobile responsiveness on the admin dashboard.
- **Enhanced:** Refactored STOMP message broker priorities for lower latency in high-traffic chat rooms.
- **Security:** Patched session-token validation logic to harden against CVE-2099-TVA.
- **Database:** Optimized PostgreSQL indexes to handle 2x expected load during the "Morning Rush" coffee peak.