# Bus Booking System — Backend

A **RESTful API** built with **Spring Boot** that powers the bus booking platform. It handles schedule management, seat status tracking, passenger bookings, and a JWT-secured admin panel.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.2 |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Utilities | Lombok |

---

## Project Structure

```
src/main/java/com/busbooking/backend/
├── controller/
│   ├── AuthController.java           # Admin login → JWT
│   ├── BookingController.java        # Book seat, search by booking code
│   ├── BusScheduleController.java    # Public: get schedules by date
│   ├── SeatController.java           # Public: get seats for a schedule
│   ├── AdminBookingController.java   # Admin: list all, cancel, change seat
│   ├── AdminBusController.java       # Admin: create / delete buses
│   ├── AdminScheduleController.java  # Admin: create / delete schedules
│   ├── AdminSeatController.java      # Admin: reserve / make available
│   └── HealthController.java         # GET /health check
├── dto/
│   ├── ApiResponse.java              # Generic wrapper { success, message, data }
│   ├── BookingResponseDTO.java       # Returned after booking a seat
│   ├── BookingSearchResponseDTO.java # Returned when searching by booking code
│   └── AdminBookingResponseDTO.java  # Admin booking list view
├── entity/
│   ├── Bus.java                      # Bus (busNumber, fromCity, toCity)
│   ├── BusSchedule.java              # Schedule (bus, travelDate, times, seats)
│   ├── Seat.java                     # Seat (seatNumber, status, schedule)
│   ├── SeatStatus.java               # Enum: AVAILABLE | RESERVED | BOOKED
│   ├── Booking.java                  # Booking (bookingCode UUID, passenger, seat)
│   └── User.java                     # Admin user (username, password, role)
├── repository/                       # Spring Data JPA interfaces
├── service/
│   ├── BookingService.java           # Core booking logic
│   └── BusScheduleService.java       # Schedule queries
├── security/
│   ├── JwtUtil.java                  # Token generation & validation
│   └── JwtAuthenticationFilter.java  # Per-request JWT filter
├── config/
│   ├── SecurityConfig.java           # Route permissions + CORS
│   └── SecurityBeans.java            # PasswordEncoder bean
└── exception/
    ├── GlobalExceptionHandler.java   # Centralised error responses
    └── SeatAlreadyBookedException.java
```

---

## API Endpoints

### Public Routes (no auth required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/health` | Health check |
| `POST` | `/api/auth/login` | Admin login — returns JWT |
| `GET` | `/api/schedules/by-date?date=YYYY-MM-DD` | Get schedules for a date |
| `GET` | `/api/seats/schedule/{scheduleId}` | Get all seats for a schedule |
| `POST` | `/api/bookings?seatId=&passengerName=&phoneNumber=` | Book a seat |
| `GET` | `/api/bookings/search?bookingCode=` | Look up a booking by UUID code |

### Admin Routes (JWT required — `ROLE_ADMIN`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/admin/bookings` | List all bookings |
| `DELETE` | `/api/admin/bookings/{id}` | Cancel a booking |
| `PUT` | `/api/admin/bookings/{id}/change-seat?newSeatId=` | Move booking to a different seat |
| `GET` | `/api/admin/buses` | List all buses |
| `POST` | `/api/admin/buses` | Create a bus |
| `DELETE` | `/api/admin/buses/{id}` | Delete a bus |
| `GET` | `/api/admin/schedules` | List all schedules |
| `POST` | `/api/admin/schedules` | Create a schedule |
| `DELETE` | `/api/admin/schedules/{id}` | Delete a schedule |
| `GET` | `/api/admin/seats/{scheduleId}` | Get seats for a schedule (admin view) |
| `PUT` | `/api/admin/seats/{id}/reserve` | Mark seat as RESERVED |
| `PUT` | `/api/admin/seats/{id}/available` | Mark seat as AVAILABLE |

---

## Features Implemented

- **JWT Authentication** — Admin login returns a signed JWT (HS256). Token expiry is 1 hour (`3600000ms`). All `/api/admin/**` routes require a valid `Authorization: Bearer <token>` header.
- **Seat Status Enum** — Each seat has one of three states: `AVAILABLE`, `RESERVED`, `BOOKED`. Booking a seat automatically changes its status to `BOOKED`.
- **Unique Booking Codes** — Each booking is assigned a UUID (`@PrePersist`) used for passenger lookup.
- **Booking Search** — Passengers can retrieve their full booking details using their booking code UUID.
- **Cancel & Re-open** — Cancelling a booking returns the seat to `AVAILABLE` automatically.
- **Stateless Security** — No HTTP sessions; every request is independently authenticated via JWT filter.
- **CORS** — Configured to allow requests from `http://localhost:3000` (the frontend).
- **Global Exception Handling** — `@RestControllerAdvice` maps known exceptions to appropriate HTTP status codes.

---

## Database Schema (auto-generated by Hibernate)

```
buses          → id, bus_number, from_city, to_city
bus_schedules  → id, bus_id (FK), travel_date, departure_time, arrival_time
seats          → id, seat_number, status, bus_schedule_id (FK)
bookings       → id, booking_code (UUID), passenger_name, phone_number, seat_id (FK)
users          → id, username, password (BCrypt), role
```

---

## How to Run

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8 running locally

### 1. Create the database

```sql
CREATE DATABASE bus_booking_db;
```

### 2. Configure credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bus_booking_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

app.jwt.secret=your_secret_key_here
app.jwt.expiration=3600000
```

### 3. Run the application

```bash
cd backend
./mvnw spring-boot:run
```

Or in IntelliJ IDEA: Run `BusBookingApplication.java`.

The API starts at **http://localhost:8080**.

### 4. Create an admin user

Insert a BCrypt-hashed password directly into the database:

```sql
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$...bcrypt_hash...', 'ROLE_ADMIN');
```

You can generate a BCrypt hash at [bcrypt-generator.com](https://bcrypt-generator.com).

---

## Environment Summary

| Setting | Value |
|---------|-------|
| Server port | `8080` |
| Database | `bus_booking_db` (MySQL) |
| JWT expiry | `3600000ms` (1 hour) |
| DDL mode | `update` (auto-creates/updates tables) |
| SQL logging | `true` (visible in console) |
