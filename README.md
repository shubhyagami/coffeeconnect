# CoffeeConnect
===============

### What is CoffeeConnect?
------------------------

CoffeeConnect is a Spring Boot application that connects colleagues for spontaneous coffee chats. It facilitates matching, peer-to-peer video calls, real-time messaging, voice recording, media sharing, and a comprehensive administrative dashboard.

### Features
------------

*   **Spontaneous Matching**: Get paired randomly with colleagues for a quick 15-minute coffee chat.
*   **Interest Tags**: Improve match quality by adding shared interests like "Latte Art" or "Java Debugging."
*   **Rich Messaging**: Real-time chat powered by WebSockets, including voice notes and media sharing.
*   **Video Calls**: Peer-to-peer WebRTC video calls with low-latency signaling.
*   **Admin Portal**: A full administrative dashboard to manage users, monitor activity, and track anonymized connection metrics.

### Technical Overview
--------------------

*   **Java 21**: Programming language used.
*   **Spring Boot 3.4.4**: Java framework used for rapid application development.
*   **PostgreSQL**: Database used for production environment.
*   **Thymeleaf + Bootstrap 5**: Template engine used for HTML page rendering.
*   **STOMP over WebSocket (SockJS)**: Real-time messaging technology used.
*   **WebRTC with raw WebSocket signaling**: Technology used for peer-to-peer video calls.
*   **Maven**: Build tool used for managing project dependencies.

### Getting Started
------------------

To set up the application:

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
3.  Open your browser and navigate to `http://localhost:8080`.

### Usage
------------

*   **Update your availability**: Set your status to "Open for Coffee" to appear in the matching queue.
*   **Use interest tags**: Add at least three tags to improve match quality. The matching algorithm favors shared interests over random pairing.
*   **Record voice notes**: Use the chat window to record quick voice messages.

### Roadmap
------------

Future updates will focus on:

*   **Local Coffee Events**: Discover and post local coffee events, as well as bean-of-the-week notifications and calendar sync.
*   **Advanced Call Features**: Introduce end-to-end encrypted calls, P2P audio fallback modes, and group huddles with breakout rooms.
*   **Connection Analytics**: Provide anonymized coffee consumption analytics, heat maps of active chats, and export org-wide connection graphs.
*   **SSO & Federation**: Implement SSO connectors (Google, GitHub, Okta), cross-org coffee invites, and a public coffee shop directory.

### License
------------

This project is licensed under the MIT License.

### Badges
------------

[![Java 21](https://img.shields.io/badge/Java-21-blue)](https://www.java.com)
[![Spring Boot 3.4.4](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue)](https://www.postgresql.org)
[![License](https://img.shields.io/badge/License-MIT-purple)](https://github.com/shubhyagami/coffeeconnect/blob/main/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)](https://github.com/shubhyagami/coffeeconnect/pulls)

### Changelog
-------------

### August 21, 2026

*   Updated README structure and formatting for better readability.
*   Enhanced Getting Started section and environment configuration details.

### August 10, 2026

*   Resolved a minor timing anomaly in the WebSocket signaling layer that occasionally delayed coffee match notifications.
