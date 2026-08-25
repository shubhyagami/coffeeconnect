# CoffeeConnect
===============

### What is CoffeeConnect?
------------------------

CoffeeConnect is a Spring Boot application that facilitates colleagues to discover, connect, and chat over coffee. It offers spontaneous matching, peer-to-peer video calls, real-time messaging, voice recording, media sharing, and a comprehensive administrative dashboard.

### Features
------------

*   **Spontaneous Matching**: Get paired randomly with colleagues for a quick 15-minute coffee chat.
*   **Interest Tags**: Improve match quality by adding shared interests like "Latte Art" or "Java Debugging."
*   **Rich Messaging**: Real-time chat powered by WebSockets, including voice notes and media sharing.
*   **Video Calls**: Peer-to-peer WebRTC video calls with low-latency signaling.
*   **Admin Portal**: A full administrative dashboard to manage users, monitor activity, and track anonymized connection metrics.

### Tech Stack
--------------

| Technology | Description |
| --- | --- |
| **Java 21** | Programming language used |
| **Spring Boot 3.4.4** | Java framework used for rapid application development |
| **PostgreSQL** | Database used for production environment |
| **JPA / Hibernate ORM 6** | Java Persistence API used for database operations |
| **Thymeleaf + Bootstrap 5** | Template engine used for HTML page rendering |
| **STOMP over WebSocket (SockJS)** | Real-time messaging technology used |
| **WebRTC with raw WebSocket signaling** | Technology used for peer-to-peer video calls |
| **Maven** | Build tool used for managing project dependencies |
| **H2 in-memory** | Local database used for development environment |
| **Render managed PostgreSQL** | Production database used with auto-scaling |

### Getting Started
------------------

### Environment Prerequisites

*   **JDK 21**
*   **Maven 3.9+**

### Running the Application
-----------------------------

1.  Clone the repository:
    ```bash
    git clone https://github.com/shubhyagami/coffeeconnect.git
    cd coffeeconnect
    ```
2.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
3.  Open `http://localhost:8080` in your browser.

### Usage Tips
--------------

*   **Set your availability**: Update your status to "Open for Coffee" to appear in the matching queue. Colleagues can then book a chat directly from your profile.
*   **Use interest tags**: Add at least three tags to improve match quality. The matching algorithm favors shared interests over random pairing.
*   **Try voice notes**: Record a quick voice message from the chat window to add a personal touch to your conversations.

### Roadmap
------------

*   **Local Coffee Events**
    *   Post and discover local coffee events inside the org.
    *   Bean-of-the-week notifications.
    *   Calendar sync with company events.
*   **Advanced Call Features**
    *   End-to-end encrypted calls.
    *   P2P audio fallback modes.
    *   Group huddles with breakout rooms.
*   **Connection Analytics**
    *   Anonymized coffee consumption analytics.
    *   Heat map of active chats.
    *   Export org-wide connection graphs.
*   **SSO & Federation**
    *   SSO connectors (Google, GitHub, Okta).
    *   Cross-org coffee invites.
    *   Public coffee shop directory.

### Changelog
-------------

### August 21, 2026

*   Updated README structure and formatting for better readability.
*   Enhanced Getting Started section and environment configuration details.

### August 10, 2026

*   Resolved a minor timing anomaly in the WebSocket signaling layer that occasionally delayed coffee match notifications.

### License
---------

This project is licensed under the MIT License. See the `LICENSE` file for details.

[![Java 21](https://img.shields.io/badge/Java-21-blue)](https://www.java.com)
[![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue)](https://www.postgresql.org)
[![License](https://img.shields.io/badge/License-MIT-purple)](https://github.com/shubhyagami/coffeeconnect/blob/main/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](https://github.com/shubhyagami/coffeeconnect/pulls)
