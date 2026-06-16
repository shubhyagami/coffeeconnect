# CoffeeConnect

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
├── controller/
│   ├── HomeController.java            # Landing page
│   ├── AuthController.java            # Login, register, verify-domain
│   ├── DashboardController.java       # Post-login dashboard
│   ├── ProfileController.java         # View/edit profile, change password
│   ├── DiscoverController.java        # Search/filter users
│   ├── CoffeeRequestController.java   # Send/accept/decline requests
│   ├── ConnectionController.java      # Manage connections
│   ├── MessageController.java         # Chat inbox, send messages
│   ├── NotificationController.java    # Notification center
│   ├── VideoCallController.java       # Video call rooms
│   ├── MediaController.java           # File upload REST API
│   └── AdminController.java           # Full admin CRUD
├── data/
│   └── SampleDataLoader.java          # Seeds 8 users, companies, etc.
├── dto/                               # 14 DTOs (request/response objects)
├── entity/                            # 14 JPA entities
├── enums/                             # 5 enums
├── repository/                        # 14 Spring Data JPA repositories
├── security/
│   ├── CustomUserDetails.java         # UserDetails wrapper
│   └── CustomUserDetailsService.java  # Loads user from DB
├── service/                           # 12 service classes
└── websocket/
    └── VideoCallWebSocketHandler.java # WebRTC signaling handler
```

---

## Database (14 Entities)

| Entity | Table | Key Fields |
|--------|-------|------------|
| User | `users` | firstName, lastName, email, password, employeeId, companyName, verificationStatus, profilePictureBase64 |
| Role | `roles` | name (`ROLE_USER`, `ROLE_ADMIN`, etc.) |
| Company | `companies` | companyName, emailDomain, headquarters, active |
| OfficeLocation | `office_locations` | companyName, campusName, city, address, latitude, longitude |
| CoffeeRequest | `coffee_requests` | sender, receiver, message, status (PENDING/ACCEPTED/DECLINED/CANCELLED) |
| Connection | `connections` | userOne, userTwo, connectedAt |
| Conversation | `conversations` | participants (many-to-many), lastMessageAt |
| Message | `messages` | conversation, sender, content, messageType (TEXT/IMAGE/AUDIO/VIDEO/VOICE/FILE), attachmentUrl |
| Notification | `notifications` | user, type, title, message, isRead |
| UserReport | `user_reports` | reporter, reportedUser, reason, details, status |
| ModerationLog | `moderation_logs` | admin, targetUser, actionType, reason |
| MessageAuditLog | `message_audit_logs` | admin, message, action |
| AuditLog | `audit_logs` | adminUsername, actionType, targetEntity, targetId, details |
| SystemSetting | `system_settings` | settingKey, settingValue, description |

---

## APIs & Routes

### Public
| Method | Path | Description |
|--------|------|-------------|
| GET | `/` or `/home` | Landing page |
| GET | `/register` | Registration form |
| POST | `/register` | Submit registration |
| GET | `/login` | Login page |
| POST | `/login` | Authenticate |
| GET | `/verify-domain` | AJAX: get email domain for company |

### User (authenticated)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/dashboard` | User dashboard |
| GET | `/profile` | View own profile |
| GET | `/profile/{id}` | View other user's profile |
| GET | `/profile/edit` | Edit profile form |
| POST | `/profile/edit` | Update profile (incl. image upload) |
| GET | `/profile/change-password` | Change password form |
| POST | `/profile/change-password` | Submit password change |
| GET | `/discover` | Search/filter users |
| GET | `/connections` | My connections |
| POST | `/connections/{id}/remove` | Remove connection |
| GET | `/coffee-requests` | My coffee requests |
| POST | `/coffee-requests/send` | Send a request |
| POST | `/coffee-requests/{id}/accept` | Accept (creates connection) |
| POST | `/coffee-requests/{id}/decline` | Decline |
| POST | `/coffee-requests/{id}/cancel` | Cancel sent request |
| GET | `/messages` | Inbox + conversation |
| POST | `/messages/send` | Send text message |
| POST | `/messages/send-media` | Send media file |
| POST | `/messages/start` | Start conversation with user |
| GET | `/notifications` | Notification center |
| POST | `/notifications/{id}/read` | Mark read |
| POST | `/notifications/read-all` | Mark all read |
| GET | `/notifications/unread-count` | JSON badge count |
| GET | `/video-call/{roomId}` | Join video call room |
| POST | `/video-call/start` | Start a video call |

### Media REST API
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/media/upload/image` | Upload image |
| POST | `/api/media/upload/audio` | Upload audio |
| POST | `/api/media/upload/voice` | Upload voice recording |
| POST | `/api/media/upload/video` | Upload video |
| GET | `/api/media/file/{type}/{filename}` | Serve uploaded file |

### Admin (requires ROLE_ADMIN or ROLE_SUPER_ADMIN)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/admin/dashboard` | Stats dashboard (Chart.js) |
| GET | `/admin/users` | User list with search/filter |
| GET | `/admin/users/{id}` | User detail |
| POST | `/admin/users/{id}/suspend` | Suspend user |
| POST | `/admin/users/{id}/activate` | Activate user |
| POST | `/admin/users/{id}/delete` | Delete user |
| POST | `/admin/users/{id}/reset-password` | Admin reset password |
| GET | `/admin/verifications` | Verification queue |
| POST | `/admin/verifications/{id}/approve` | Approve verification |
| POST | `/admin/verifications/{id}/reject` | Reject verification |
| GET | `/admin/companies` | List companies |
| POST | `/admin/companies` | Add company |
| POST | `/admin/companies/{id}/update` | Update company |
| POST | `/admin/companies/{id}/toggle` | Enable/disable |
| POST | `/admin/companies/{id}/delete` | Delete company |
| GET | `/admin/offices` | List offices |
| POST | `/admin/offices` | Add office |
| POST | `/admin/offices/{id}/update` | Update office |
| POST | `/admin/offices/{id}/delete` | Delete office |
| GET | `/admin/connections` | All connections |
| POST | `/admin/connections/{id}/remove` | Force-remove connection |
| GET | `/admin/requests` | All coffee requests |
| POST | `/admin/requests/{id}/cancel` | Cancel any request |
| GET | `/admin/reports` | User reports |
| POST | `/admin/reports/{id}/dismiss` | Dismiss report |
| POST | `/admin/reports/{id}/warn` | Warn user |
| POST | `/admin/reports/{id}/suspend` | Suspend user |
| POST | `/admin/reports/{id}/ban` | Ban user |
| GET | `/admin/settings` | System settings form |
| POST | `/admin/settings/update` | Save settings |
| GET | `/admin/audit-logs` | Audit log viewer |
| GET | `/admin/notifications` | Send notification form |
| POST | `/admin/notifications/send` | Send global/company/campus notification |

---

## WebSocket Endpoints

| Endpoint | Protocol | Purpose |
|----------|----------|---------|
| `/ws` | STOMP + SockJS | Real-time messaging |
| `/ws-call` | Raw WebSocket | WebRTC video call signaling |

### STOMP Destinations
- **Broker (subscribe):** `/topic/*`, `/queue/*`, `/user/*`
- **Application (publish):** `/app/*`

---

## Security

- **Session-based authentication** (no JWT)
- **BCrypt password hashing**
- **4 roles**: `ROLE_USER`, `ROLE_MODERATOR`, `ROLE_ADMIN`, `ROLE_SUPER_ADMIN`
- **Role hierarchy**: `/admin/**` requires ADMIN or SUPER_ADMIN; `/super-admin/**` requires SUPER_ADMIN
- **CSRF**: disabled for `/h2-console/**`, `/ws/**`, `/api/**`, `/uploads/**`
- **Session**: fixation migration, max 1 session per user
- **Remember-me**: 7-day cookie

---

## Configuration Profiles

### Local (`application.yml` — default)
- H2 in-memory database (`jdbc:h2:mem:coffeeconnect`)
- H2 console at `/h2-console`
- Thymeleaf cache disabled
- SQL logging enabled

### Render (`application-render.yml` — profile: `render`)
- PostgreSQL via `RENDER_DATABASE_URL` env var
- Thymeleaf cache enabled
- SQL logging disabled
- `ddl-auto: update`

---

## UI Design (Brutalism)

- **Square corners** (no border-radius)
- **Thick black borders** (`3px solid #000`)
- **Monospace font** (`'Courier New', Courier, monospace`)
- **Impact headings** (`font-family: Impact, ...`)
- **Hard shadows** (`box-shadow: 5px 5px 0px #000`)
- **Uppercase labels**
- All 27 templates follow this design

---

## Seed Data

On first run (empty DB), `SampleDataLoader` creates:
- **4 roles** (ROLE_USER through ROLE_SUPER_ADMIN)
- **5 companies** (TCS, Infosys, Accenture, Wipro, Cognizant)
- **8 users** (1 super admin + 7 employees across 5 companies)
- **7 office locations** (Mumbai, Bangalore x2, Mysore, Hyderabad, Chennai, Pune)
- **3 coffee requests** (1 accepted, 2 pending)
- **1 connection** + **1 conversation** + **2 messages**
- **2 notifications**
- **1 user report**
- **5 audit logs**
- **5 system settings**

---

## Deploy to Render

The project includes `render.yaml` for one-click deployment:

1. Push repo to GitHub
2. On Render dashboard, select "Blueprint" and connect your repo
3. Render provisions a web service + PostgreSQL database automatically
4. Database connection string is injected via `RENDER_DATABASE_URL` env var
5. Active profile is set to `render`

### Manual Deploy
```bash
mvn clean package -DskipTests
# Upload JAR to Render and set:
#   Start Command: java -Dserver.port=$PORT $JAVA_OPTS -jar target/coffeeconnect-1.0.0.jar
#   SPRING_PROFILES_ACTIVE: render
```

---

## Key Design Decisions

| Decision | Rationale |
|----------|-----------|
| Session auth over JWT | Thymeleaf server-side rendering doesn't need stateless tokens |
| Base64 profile images in DB | Simpler than managing file storage for profile pics across environments |
| Raw WebSocket for video calls | Lightweight signaling separate from STOMP messaging |
| File-based uploads for media | Attachments (images/audio/video) stored on disk under `uploads/` |
| Lombok | Reduces boilerplate on entities/DTOs |
| H2 in-memory locally | Zero setup, data resets on restart (file mode available for persistence) |
| `ddl-auto: update` | Schema auto-migrates; no Flyway/Liquibase needed for this scale |
| Role-based admin routes | Simple `antMatchers` without method-level annotations for most endpoints |
