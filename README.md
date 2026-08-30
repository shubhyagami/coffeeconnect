# CoffeeConnect  

A Spring Boot application that enables spontaneous coffee chats among colleagues. It offers random 15‑minute matches, interest‑based tagging, real‑time messaging, peer‑to‑peer video calls, and an admin dashboard for monitoring and analytics.  

---  

## Overview  

CoffeeConnect pairs coworkers for short, informal coffee conversations. Users can add interest tags to improve match relevance, exchange messages with media support, start video calls via WebRTC, and view analytics through an admin portal.  

---  

## Features  

- **Spontaneous Matching** – Connect with a random colleague for a 15‑minute coffee chat.  
- **Interest Tags** – Add topics such as “Latte Art”, “Java Debugging”, etc., to help surface relevant matches.  
- **Rich Messaging** – Real‑time chat via WebSockets with voice notes and media sharing.  
- **Video Calls** – Peer‑to‑peer WebRTC calls with low‑latency signaling.  
- **Admin Portal** – Dashboard for user management, activity monitoring, and anonymized analytics.  

---  

## Technical Overview  

- **Language:** Java 21  
- **Framework:** Spring Boot 3.4.4  
- **Database:** PostgreSQL  
- **Frontend:** Thymeleaf + Bootstrap 5  
- **WebSocket:** STOMP over SockJS  
- **WebRTC:** Peer‑to‑peer media with raw WebSocket signaling  
- **Build Tool:** Maven  

---  

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

---  

## Usage  

- Set your status to **“Open for Coffee”** to join the matching queue.  
- Add at least three interest tags to improve match relevance.  
- Record and send voice notes directly in the chat window.  

---  

## Roadmap  

- Calendar integration for local coffee events and “bean‑of‑the‑week” notifications.  
- End‑to‑end encryption for calls, P2P audio fallback, and support for group huddles.  
- Connection analytics, heat‑map visualizations, and exportable org‑wide connection graphs.  
- SSO connectors (Google, GitHub, Okta), cross‑organization coffee invites, and a public coffee‑shop directory.  

---  

## License  

MIT © 2026  

---  

## Badges  

[![Java 21](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/technologies/downloads/)  
[![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)](https://www.postgresql.org)  
[![License](https://img.shields.io/badge/License-MIT-purple)](https://github.com/shubhyagami/coffeeconnect/blob/main/LICENSE)  
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](https://github.com/shubhyagami/coffeeconnect/pulls)  

---  

## Changelog  

- **August 21, 2026** – Refined README structure and clarified environment configuration.  
- **August 10, 2026** – Fixed a WebSocket timing issue that intermittently delayed match notifications.  
- **July 15, 2026** – Added interest‑tag support and expanded voice‑note functionality.  

---  

## Contributing  

Pull requests are welcome. Please open an issue first to discuss any major changes.  

---  

## Contact  

For questions or feedback, reach out to the maintainer at **shubhyagami** on GitHub.
