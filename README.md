# Airline Management System

This repository contains the original Travel Booking System alongside a Spring Boot application located in `spring-boot-app`.

The Spring Boot application reuses the existing DAO and DTO packages to expose REST services modeled after the legacy client and server.

## Flight endpoints

- `GET /flights` – list all flights
- `GET /flights/{flightNumber}` – retrieve a flight by number
- `POST /flights` – insert a flight (JSON body matching `Application.DTOs.Flight`)
- `DELETE /flights/{flightNumber}` – delete a flight by number

## Building

```
cd spring-boot-app
mvn clean package
```

## Running

```
cd spring-boot-app
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).
