[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
# CoffeeConnect

CoffeeConnect is a lightweight Spring Boot application that pairs coworkers for short, spontaneous coffee chats. When a user marks themselves **Open for Coffee**, the system automatically finds another available colleague and creates a private session. Inside a session, participants can exchange text, images, voice notes, or start a low-latency WebRTC video call. The admin dashboard at `/admin` shows users, active sessions, and key metrics.

![Build status](https://github.com/shubhyagami/coffeeconnect/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/github/license/shubhyagami/coffeeconnect)
![Code coverage](https://img.shields.io/codecov/c/github/shubhyagami/coffeeconnect)
![Java 21](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Local Maven Run](#local-maven-run)
  - [Docker Compose](#docker-compose)
- [Configuration](#configuration)
- [Using the App](#using-the-app)
- [Testing & Formatting](#testing--formatting)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

---

## Overview

- **Java 21 & Spring Boot 3.4.4**
- Persistence: PostgreSQL, or H2 for local development
- Real-time chat: WebSocket/STOMP, WebRTC
- Admin UI: `/admin`
- Hot-reload
