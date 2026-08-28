# CoffeeConnect
===============

## Overview
CoffeeConnect is a Spring Boot application that facilitates spontaneous coffee chats between colleagues. It supports random 15‑minute matches, interest‑based tagging, real‑time messaging, peer‑to‑peer video calls, and an admin dashboard for monitoring and analytics.

## Features
- **Spontaneous Matching** – Pair with a colleague for a quick 15‑minute coffee chat.  
- **Interest Tags** – Add shared interests (e.g., “Latte Art”, “Java Debugging”) to improve match relevance.  
- **Rich Messaging** – Real‑time chat powered by WebSockets, including voice notes and media sharing.  
- **Video Calls** – Peer‑to‑peer WebRTC calls with low‑latency signaling.  
- **Admin Portal** – Dashboard for user management, activity monitoring, and anonymized metrics.

## Technical Overview
- **Java 21**
- **Spring Boot 3.4.4**
- **PostgreSQL**
- **Thymeleaf + Bootstrap 5**
- **STOMP over WebSocket (SockJS)**
- **WebRTC with raw WebSocket signaling**
- **Maven**

## Getting Started
### Prerequisites
- JDK 21
- Maven 3.9+

### Run the Application
```bash
git clone https://github.com/shubhyagami/coffeeconnect.git
cd coffeeconnect
mvn spring-boot:run
```
Open a browser at **http://localhost:8080**.

## Usage
- Set your status to **“Open for Coffee”** to join the matching queue.  
- Add at least three interest tags to improve match quality.  
- Record voice notes directly in the chat window.

## Roadmap
- Local coffee events, calendar sync, and bean‑of‑the‑week notifications.  
- End‑to‑end encrypted calls, P2P audio fallback, and group huddles.  
- Connection analytics, heat maps, and exportable org‑wide connection graphs.  
- SSO connectors (Google, GitHub, Okta), cross‑org coffee invites, and a public coffee‑shop directory.

## License
MIT © 2026

## Badges
[![Java 21](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/technologies/downloads/)  
[![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue)](https://www.postgresql.org)  
[![License](https://img.shields.io/badge/License-MIT-purple)](https://github.com/shubhyagami/coffeeconnect/blob/main/LICENSE)  
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](https://github.com/shubhyagami/coffeeconnect/pulls)

## Changelog
- **August 21, 2026** – Updated README structure and clarified environment configuration.  
- **August 10, 2026** – Fixed a timing issue in the WebSocket signaling layer that occasionally delayed match notifications.
